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

/**
 * 사용자 정보를 관리하는 엔티티 클래스입니다.
 * <p>
 * 이메일, 비밀번호(암호화), 닉네임, 자기소개 등의 사용자 정보를 저장합니다.
 * JPA Auditing을 통해 생성일시와 수정일시가 자동으로 관리됩니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Table(name = "users")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    /**
     * 사용자 고유 ID (자동 생성)
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 사용자 이메일 (로그인 ID로 사용, 고유값)
     */
    private String email;

    /**
     * 사용자 닉네임 (화면에 표시되는 이름)
     */
    private String username;

    /**
     * 암호화된 비밀번호 (BCrypt 방식)
     */
    private String password;

    /**
     * 사용자 자기소개
     */
    private String bio;

    /**
     * 계정 생성일 (JPA Auditing으로 자동 설정)
     */
    @CreatedDate
    private LocalDate createdAt;

    /**
     * 계정 수정일 (JPA Auditing으로 자동 업데이트)
     */
    @LastModifiedDate
    private LocalDate updatedAt;

    /**
     * User 엔티티 생성자
     *
     * @param email    이메일
     * @param username 닉네임
     * @param password 암호화된 비밀번호
     * @param bio      자기소개
     */
    @Builder
    public User(String email, String username, String password, String bio) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

    /**
     * UserRequest DTO로부터 User 엔티티를 생성합니다.
     *
     * @param request        회원가입 요청 DTO
     * @param passwordEncode 암호화된 비밀번호
     * @return User 엔티티
     */
    public static User form(UserRequest request, String passwordEncode) {
        return User.builder()
                .email(request.email())
                .password(passwordEncode)
                .username(request.nickname())
                .bio(request.bio())
                .build();
    }
}
