package io.github.tato126.practice.config;

import io.github.tato126.practice.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정
 *
 * Phase 1: 모든 요청 허용 (개발 편의)
 * Phase 2: JWT 인증 적용
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // REST API이므로 CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                // Phase 1: 모든 요청 허용
                .requestMatchers("/h2-console/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                .anyRequest().permitAll()

                // Phase 2: 인증 적용 시 아래 주석 해제
                // .requestMatchers("/api/auth/**", "/h2-console/**", "/swagger-ui/**").permitAll()
                // .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())  // H2 Console 사용을 위해
            )
            // JWT 필터 추가 (UsernamePasswordAuthenticationFilter 전에 실행)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
