package io.github.tato126.practice.post.dto.response;

import io.github.tato126.practice.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 게시글 정보 응답 DTO입니다.
 * <p>
 * 클라이언트에게 게시글 정보를 전달할 때 사용합니다.
 * Entity를 직접 노출하지 않고 필요한 정보만 제공합니다.
 * </p>
 *
 * @param id        게시글 ID
 * @param title     게시글 제목
 * @param content   게시글 내용
 * @param author    작성자명
 * @param status    게시 상태 (DRAFT/PUBLISHED)
 * @param createdAt 생성일시
 * @param updatedAt 수정일시
 * @author tato126
 * @since 1.0
 */
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

    /**
     * Post 엔티티를 PostResponse DTO로 변환합니다.
     *
     * @param post Post 엔티티
     * @return PostResponse DTO
     */
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
