package io.github.tato126.practice.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "댓글 내용은 필수입니다")
        @Size(max = 500, message = "댓글은 500자 이내로 작성해주세요")
        String content
) {
}
