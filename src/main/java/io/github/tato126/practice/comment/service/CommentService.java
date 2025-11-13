package io.github.tato126.practice.comment.service;

import io.github.tato126.practice.comment.dto.CommentRequest;
import io.github.tato126.practice.comment.dto.CommentResponse;
import io.github.tato126.practice.comment.entity.Comment;
import io.github.tato126.practice.comment.repository.CommentRepository;
import io.github.tato126.practice.common.excetion.comment.CommentAccessDeniedException;
import io.github.tato126.practice.common.excetion.comment.CommentNotFoundException;
import io.github.tato126.practice.common.excetion.login.UserNotFoundException;
import io.github.tato126.practice.common.excetion.post.PostNotFoundException;
import io.github.tato126.practice.post.entity.Post;
import io.github.tato126.practice.post.repository.PostRepository;
import io.github.tato126.practice.user.entity.User;
import io.github.tato126.practice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 작성
    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest commentRequest, String userEmail) {

        // 사용자 조회 (인증된 사용자)
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        // 게시글 조회 (댓글 달 대상)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다. ID: " + postId));

        // commentRequest -> entity
        Comment newComment = Comment.form(post, commentRequest, author);

        Comment comment = commentRepository.save(newComment);

        return CommentResponse.form(comment);
    }

    // 수정
    @Transactional
    public CommentResponse updateComment(Long commentId, String updateContent, String userEmail) {

        // 삭제되지 않은 댓글만 조회
        Comment updateComment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다. ID: " + commentId));

        // 댓글 수정 권한 검증 (작성자 본인만 수정 가능)
        if (!updateComment.getAuthor().getEmail().equals(userEmail)) {
            throw new CommentAccessDeniedException("댓글 수정 권한이 없습니다.");
        }

        // comment 내용 수정
        updateComment.update(updateContent);

        // entity -> dto
        return CommentResponse.form(updateComment);
    }

    // 목록 조회
    public Page<CommentResponse> readComments(Long postId, Pageable pageable) {

        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 포스트를 찾을 수 없습니다. ID: " + postId));

        // 삭제되지 않은 댓글만 조회
        Page<Comment> comments = commentRepository.findAllByPostIdAndIsDeletedFalse(postId, pageable);

        // entity -> Page<dto>
        return comments.map(CommentResponse::form);
    }

    // 삭제
    @Transactional
    public void deleteComment(Long commentId, String userEmail) {

        // 삭제되지 않은 댓글만 조회
        Comment deleteComment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CommentNotFoundException("존재하지 않은 댓글입니다. ID: " + commentId));

        // 본인 인증 체크
        if (!deleteComment.getAuthor().getEmail().equals(userEmail)) {
            throw new CommentAccessDeniedException("댓글 삭제 권한이 없습니다.");
        }

        // 댓글 삭제 (soft-delete)
        deleteComment.delete();
    }
}
