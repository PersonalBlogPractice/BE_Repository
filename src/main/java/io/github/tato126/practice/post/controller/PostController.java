package io.github.tato126.practice.post.controller;

import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

    private final PostService postService;

    // 작성글 생성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) {
        return postService.register(postRequest);
    }

    // 게시글 전체 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PostResponse> getPosts(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAllPosts(pageable);
    }

    // 게시글 단일 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.findByPostId(id);
    }
}
