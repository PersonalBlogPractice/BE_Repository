package io.github.tato126.practice.post.dto.request;

public record PostUpdateRequest(
        String title,
        String content
) {
}
