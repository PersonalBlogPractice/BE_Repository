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

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
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

    // 게시글 전체 조회
    public Page<PostResponse> findAllPosts(Pageable pageable) {

        // PUBLISH 상태만 조회
        Page<Post> posts = postRepository.findAllByStatus(Post.PostStatus.PUBLISHED, pageable);

        // dto -> response
        return posts.map(PostResponse::form);
    }

    // 게시글 단일 조회
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
