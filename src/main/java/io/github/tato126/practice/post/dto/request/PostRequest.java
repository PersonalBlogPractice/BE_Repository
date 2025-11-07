package io.github.tato126.practice.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostRequest(

        @NotBlank(message = "제목은 필수 입니다.")
        @Size(min = 10, max = 100, message = "제목은 10~100자여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 10, max = 250, message = "내용은 최소 10자 이상이여야 합니다.")
        String content
) {
}
