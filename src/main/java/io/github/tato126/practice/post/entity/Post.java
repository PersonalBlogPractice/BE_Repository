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
        this.status = status;
        this.authorName = authorName;
    }

    public static Post form(PostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }
}
