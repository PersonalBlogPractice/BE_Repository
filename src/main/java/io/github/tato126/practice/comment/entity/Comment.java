package io.github.tato126.practice.comment.entity;

import io.github.tato126.practice.comment.dto.CommentRequest;
import io.github.tato126.practice.post.entity.Post;
import io.github.tato126.practice.user.entity.User;
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
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String content;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private Boolean isDeleted;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Comment(String content, User author, Post post, Boolean isDeleted) {
        this.content = content;
        this.author = author;
        this.post = post;
        this.isDeleted = isDeleted;
    }

    public static Comment form(Post post, CommentRequest commentRequest, User user) {
        return Comment.builder()
                .content(commentRequest.content())
                .author(user)
                .post(post)
                .isDeleted(false)
                .build();
    }
}
