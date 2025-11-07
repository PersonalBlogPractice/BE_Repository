package io.github.tato126.practice.post.controller;

import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.post.dto.response.PostResponse;
import io.github.tato126.practice.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "포스트", description = "포스트 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

    private final PostService postService;

    @Operation(summary = "포스트 생성", description = "새로운 포스트를 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) {
        return postService.register(postRequest);
    }

    @Operation(
            summary = "포스트 목록 조회",
            description = "발행된 포스트 목록을 페이징하여 조회합니다. (기본: 20개씩, 생성일시 내림차순)"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PostResponse> getPosts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return postService.findAllPosts(pageable);
    }

    @Operation(summary = "포스트 상세 조회", description = "특정 포스트의 상세 정보를 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PostResponse getPost(
            @Parameter(description = "포스트 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        return postService.findByPostId(id);
    }
}
