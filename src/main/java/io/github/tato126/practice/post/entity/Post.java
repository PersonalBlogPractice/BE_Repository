package io.github.tato126.practice.post.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.DOING;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum PostStatus {
        DOING,
        DONE
    }
}
