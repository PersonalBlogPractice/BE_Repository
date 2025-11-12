package io.github.tato126.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * DevBlog 애플리케이션의 메인 클래스입니다.
 * <p>
 * Spring Boot 기반의 개발자 블로그 플랫폼 RESTful API 애플리케이션을 시작합니다.
 * JPA Auditing을 활성화하여 엔티티의 생성/수정 시각을 자동으로 관리합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@EnableJpaAuditing
@SpringBootApplication
public class PracticeApplication {

    /**
     * 애플리케이션 진입점입니다.
     *
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

}
