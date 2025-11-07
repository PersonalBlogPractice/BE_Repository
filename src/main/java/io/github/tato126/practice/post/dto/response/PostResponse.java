package io.github.tato126.practice.post.dto.response;

import io.github.tato126.practice.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        Post.PostStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static PostResponse form(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthorName())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
