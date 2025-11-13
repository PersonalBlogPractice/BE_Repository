package io.github.tato126.practice.comment.controller;

import io.github.tato126.practice.comment.dto.CommentRequest;
import io.github.tato126.practice.comment.dto.CommentResponse;
import io.github.tato126.practice.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    // 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{postId}/comments")
    public CommentResponse write(@PathVariable Long postId, @Valid @RequestBody CommentRequest request, @AuthenticationPrincipal String userEmail) {
        return commentService.createComment(postId, request, userEmail);
    }

    // 댓글 읽기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResponse> readAll(@PathVariable Long postId, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.readComments(postId, pageable);
    }

    // 수정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/comments/{commentId}")
    public CommentResponse update(@PathVariable Long commentId, @RequestBody String comment, @AuthenticationPrincipal String userEmail) {
        return commentService.updateComment(commentId, comment, userEmail);
    }

    // 댓글 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long commentId, @AuthenticationPrincipal String userEmail) {
        commentService.deleteComment(commentId, userEmail);
    }
}
