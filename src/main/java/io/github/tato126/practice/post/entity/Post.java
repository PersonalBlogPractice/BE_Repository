package io.github.tato126.practice.post.entity;

import io.github.tato126.practice.post.dto.request.PostRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 블로그 게시글 정보를 관리하는 엔티티 클래스입니다.
 * <p>
 * 제목, 내용, 작성자명, 게시 상태(DRAFT/PUBLISHED) 등의 게시글 정보를 저장합니다.
 * JPA Auditing을 통해 생성일시와 수정일시가 자동으로 관리됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    private String authorName;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.DRAFT;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum PostStatus {
        DRAFT,
        PUBLISHED
    }

    @Builder
    public Post(String title, String content, String authorName, PostStatus status) {
        this.title = title;
        this.content = content;
        this.authorName = (authorName != null) ? authorName : "nameIsNull";
        this.status = (status != null) ? status : PostStatus.DRAFT;
    }

    public static Post form(PostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }
}
