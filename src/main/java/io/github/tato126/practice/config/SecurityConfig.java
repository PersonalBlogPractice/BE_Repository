package io.github.tato126.practice.config;

import io.github.tato126.practice.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 보안 설정 클래스입니다.
 * <p>
 * JWT 기반 인증을 위한 보안 필터 체인과 비밀번호 인코더를 설정합니다.
 * Phase 2에서 JWT 인증 필터를 적용하여 API 보안을 강화합니다.
 * </p>
 * <ul>
 *   <li>Phase 1: 개발 편의를 위해 모든 요청 허용</li>
 *   <li>Phase 2: JWT 인증 적용 (인증이 필요한 엔드포인트 보호)</li>
 * </ul>
 *
 * @author tato126
 * @since 1.0
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Spring Security 필터 체인을 구성합니다.
     * <p>
     * CSRF 비활성화, 요청 권한 설정, JWT 필터 등록 등을 수행합니다.
     * </p>
     *
     * @param http HttpSecurity 설정 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception 보안 설정 중 오류 발생 시
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // REST API이므로 CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                // 인증 없이 접근 가능한 엔드포인트
                .requestMatchers("/api/auth/**", "/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/**").permitAll()  // 게시글 조회만 허용
                .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()  // 사용자 프로필 조회 허용
                // 나머지는 인증 필요 (POST, PUT, DELETE)
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())  // H2 Console 사용을 위해
            )
            // JWT 필터 추가 (UsernamePasswordAuthenticationFilter 전에 실행)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 BCrypt 인코더 Bean을 생성합니다.
     * <p>
     * 회원가입 시 비밀번호를 암호화하고, 로그인 시 비밀번호를 검증하는 데 사용됩니다.
     * </p>
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
