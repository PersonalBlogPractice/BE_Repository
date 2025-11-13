package io.github.tato126.practice.comment.repository;

import io.github.tato126.practice.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 삭제되지 않은 댓글만 조회 (목록)
    Page<Comment> findAllByPostIdAndIsDeletedFalse(Long postId, Pageable pageable);

    // 삭제되지 않은 댓글 단건 조회 (수정/삭제 시 사용)
    Optional<Comment> findByIdAndIsDeletedFalse(Long id);
}
