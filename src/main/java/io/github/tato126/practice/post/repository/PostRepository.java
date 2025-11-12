package io.github.tato126.practice.post.repository;

import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시글 데이터 접근을 위한 Repository 인터페이스입니다.
 * <p>
 * Spring Data JPA를 사용하여 Post 엔티티의 CRUD 작업을 처리합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 특정 상태의 게시글 목록을 페이징하여 조회합니다.
     *
     * @param postStatus 조회할 게시글 상태 (DRAFT/PUBLISHED)
     * @param pageable   페이지 정보 (페이지 번호, 크기, 정렬)
     * @return 해당 상태의 게시글 목록 (페이징 처리됨)
     */
    Page<Post> findAllByStatus(Post.PostStatus postStatus, Pageable pageable);

}
