package io.github.tato126.practice.post.dto.request;

import io.github.tato126.practice.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * 게시글 생성/수정 요청 DTO입니다.
 * <p>
 * 클라이언트로부터 게시글 정보를 받아올 때 사용합니다.
 * 제목과 내용에 대한 유효성 검증을 수행합니다.
 * </p>
 *
 * @param title   게시글 제목 (3~20자)
 * @param content 게시글 내용 (5~250자)
 * @param status  게시글 상태 (DRAFT 또는 PUBLISHED, nullable - 기본값: DRAFT)
 * @author tato126
 * @since 1.0
 */
@Builder
public record PostRequest(

        @NotBlank(message = "제목은 필수 입니다.")
        @Size(min = 3, max = 20, message = "제목은 3~20자여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(min = 5, max = 250, message = "내용은 최소 10자 이상이여야 합니다.")
        String content,

        Post.PostStatus status
) {
}
