package io.github.tato126.practice.comment.dto;

import io.github.tato126.practice.comment.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        Long id,
        String content,
        Long authorId,
        String authorNickname,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isDeleted
) {

    public static CommentResponse form(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .authorNickname(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .isDeleted(comment.getIsDeleted())
                .build();
    }
}
