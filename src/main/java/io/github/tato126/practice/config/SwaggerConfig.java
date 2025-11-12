package io.github.tato126.practice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 문서화 설정 클래스입니다.
 * <p>
 * API 문서 자동 생성 및 JWT 인증 테스트를 위한 Swagger UI를 설정합니다.
 * Bearer 토큰 방식의 JWT 인증을 Swagger에서 테스트할 수 있도록 구성합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    /**
     * OpenAPI 기본 설정을 구성합니다.
     * <p>
     * API 정보, JWT 보안 스키마, 보안 요구사항을 설정합니다.
     * </p>
     *
     * @return 구성된 OpenAPI 객체
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(info());
    }

    /**
     * JWT Bearer 인증을 위한 보안 스키마를 생성합니다.
     *
     * @return SecurityScheme 객체
     */
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT 토큰을 입력하세요 (Bearer 제외)");
    }

    /**
     * API 문서의 기본 정보를 생성합니다.
     *
     * @return Info 객체
     */
    private Info info() {
        return new Info()
                .title("DevBlog API")
                .description("개발자 블로그 플랫폼 RESTful API")
                .version("1.0.0");
    }

    /**
     * API 그룹을 정의합니다.
     * <p>
     * /api/** 경로의 모든 엔드포인트를 "public" 그룹으로 묶습니다.
     * </p>
     *
     * @return GroupedOpenApi 객체
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
