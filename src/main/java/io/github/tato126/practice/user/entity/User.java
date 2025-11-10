package io.github.tato126.practice.user.entity;

import io.github.tato126.practice.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Table(name = "users")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // email
    private String email;

    // username
    private String username;

    // password
    private String password;

    // bio
    private String bio;

    // createdAt
    @CreatedDate
    private LocalDate createdAt;

    // updatedAt
    @LastModifiedDate
    private LocalDate updatedAt;

    @Builder
    public User(String email, String username, String password, String bio) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

    public static User form(UserRequest request, String passwordEncode) {
        return User.builder()
                .email(request.email())
                .password(passwordEncode)
                .username(request.nickname())
                .bio(request.bio())
                .build();
    }
}
