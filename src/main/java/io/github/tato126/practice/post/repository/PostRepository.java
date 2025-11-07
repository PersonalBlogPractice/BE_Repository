package io.github.tato126.practice.post.repository;

import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByStatus(Post.PostStatus postStatus, Pageable pageable);

}
