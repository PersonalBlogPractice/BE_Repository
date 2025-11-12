package io.github.tato126.practice.post.service;

import io.github.tato126.practice.common.excetion.PostAccessDeniedException;
import io.github.tato126.practice.common.excetion.PostNotFoundException;
import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.entity.Post;
import io.github.tato126.practice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 게시글 생성, 조회(목록/단건), 수정, 삭제 등의 기능을 제공하며,
 * PUBLISHED 상태의 게시글만 공개되도록 제어합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    /**
     * 새로운 게시글을 생성합니다.
     * <p>
     * 기본 상태는 DRAFT로 설정되며, 작성자 정보는 요청 데이터에서 가져옵니다.
     * </p>
     *
     * @param request 게시글 생성 요청 정보 (제목, 내용)
     * @return 생성된 게시글 정보
     */
    public PostResponse register(PostRequest request) {

        log.debug("request : " + request.toString());
        // request -> entity
        Post newPost = Post.form(request);

        log.debug("newPost: " + newPost.toString());

        // repository save
        postRepository.save(newPost);

        // entity -> response
        return PostResponse.form(newPost);
    }

    /**
     * 발행된 게시글 목록을 페이징하여 조회합니다.
     * <p>
     * PUBLISHED 상태의 게시글만 조회되며, DRAFT 상태는 제외됩니다.
     * 기본 정렬은 생성일시 내림차순입니다.
     * </p>
     *
     * @param pageable 페이지 정보 (페이지 번호, 크기, 정렬)
     * @return 게시글 목록 (페이징 처리됨)
     */
    public Page<PostResponse> findAllPosts(Pageable pageable) {

        // PUBLISH 상태만 조회
        Page<Post> posts = postRepository.findAllByStatus(Post.PostStatus.PUBLISHED, pageable);

        // dto -> response
        return posts.map(PostResponse::form);
    }

    /**
     * 특정 게시글의 상세 정보를 조회합니다.
     * <p>
     * PUBLISHED 상태의 게시글만 조회 가능하며, DRAFT 상태인 경우 접근이 거부됩니다.
     * </p>
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 상세 정보
     * @throws PostNotFoundException 게시글이 존재하지 않는 경우
     * @throws PostAccessDeniedException 비공개(DRAFT) 게시글에 접근하는 경우
     */
    public PostResponse findByPostId(Long id) {

        // post 조회
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("포스트를 찾을 수 없습니다. ID: " + id));

        // draft 면 비공개 포스트 입니다.
        if (post.getStatus() == Post.PostStatus.DRAFT) {
            throw new PostAccessDeniedException("비공개 포스트입니다.");
        }

        return PostResponse.form(post);
    }

}
