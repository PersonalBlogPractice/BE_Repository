package io.github.tato126.practice.post.dto.request;

import io.github.tato126.practice.post.entity.Post;

/**
 * 게시글 수정 요청 DTO입니다.
 * <p>
 * 모든 필드가 nullable이며, null이 아닌 필드만 업데이트됩니다.
 * </p>
 *
 * @param title   게시글 제목 (nullable)
 * @param content 게시글 내용 (nullable)
 * @param status  게시글 상태 (nullable)
 * @author tato126
 * @since 1.0
 */
public record PostUpdateRequest(
        String title,
        String content,
        Post.PostStatus status
) {
}
