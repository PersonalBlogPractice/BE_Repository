package io.github.tato126.practice.post.entity;

import io.github.tato126.practice.post.dto.request.PostRequest;
import io.github.tato126.practice.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

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

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Post(String title, String content, User author, PostStatus status) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.status = (status != null) ? status : PostStatus.DRAFT;
    }

    public static Post form(PostRequest request, User author) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
    }
}
