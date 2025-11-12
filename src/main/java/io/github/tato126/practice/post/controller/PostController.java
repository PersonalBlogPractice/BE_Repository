package io.github.tato126.practice.post.controller;

import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.post.dto.request.PostUpdateRequest;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * <p>
 * 게시글 생성, 목록 조회, 상세 조회 등의 REST API 엔드포인트를 제공합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Tag(name = "포스트", description = "포스트 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

    private final PostService postService;

    @Operation(summary = "포스트 생성", description = "새로운 포스트를 생성합니다. (인증 필요)")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostResponse createPost(
            @Valid @RequestBody PostRequest postRequest,
            @Parameter(hidden = true) @AuthenticationPrincipal String userEmail
    ) {
        return postService.register(postRequest, userEmail);
    }

    @Operation(summary = "포스트 수정", description = "포스트를 수정합니다. (작성자 본인만 가능)")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public PostResponse updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest postUpdateRequest,
            @Parameter(hidden = true) @org.springframework.security.core.annotation.AuthenticationPrincipal String userEmail
    ) {
        return postService.update(id, postUpdateRequest, userEmail);
    }

    @Operation(summary = "포스트 삭제", description = "포스트를 삭제합니다. (작성자 본인만 가능)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePost(
            @PathVariable Long id,
            @Parameter(hidden = true) @org.springframework.security.core.annotation.AuthenticationPrincipal String userEmail
    ) {
        postService.delete(id, userEmail);
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
