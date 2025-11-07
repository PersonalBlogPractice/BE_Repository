package io.github.tato126.practice.post.entity;

import io.github.tato126.practice.post.dto.request.PostRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    private String authorName;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.DRAFT;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum PostStatus {
        DRAFT,
        PUBLISHED
    }

    @Builder
    public Post(String title, String content, PostStatus postStatus) {
        this.title = title;
        this.content = content;
        this.postStatus = postStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Post form(PostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }
}
