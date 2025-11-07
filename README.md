# DevBlog - 개발자 블로그 플랫폼 API

Spring Boot 기반의 RESTful API 프로젝트

## 📋 프로젝트 개요

개발자들이 기술 블로그를 작성하고 공유할 수 있는 백엔드 API

## 🛠️ 기술 스택

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.7
- **Build Tool:** Gradle
- **Database:** H2 (In-Memory)
- **ORM:** Spring Data JPA
- **Security:** Spring Security + JWT
- **API Documentation:** SpringDoc OpenAPI (Swagger)
- **Query:** QueryDSL

## 🚀 시작하기

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd practice
```

### 2. 실행

```bash
./gradlew bootRun
```

### 3. 접속 확인

- **애플리케이션:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:devblog`
  - Username: `sa`
  - Password: (공백)

## 📦 주요 기능

### Phase 1: 포스트 관리 ✅
- [x] 포스트 CRUD
- [x] 페이징 및 정렬
- [x] 입력 검증

### Phase 2: 회원 & 인증 (진행 예정)
- [ ] 회원가입/로그인
- [ ] JWT 토큰 인증
- [ ] 권한 관리

### Phase 3: 댓글 & 태그 (진행 예정)
- [ ] 댓글 시스템
- [ ] 태그 기반 분류
- [ ] N:M 관계 매핑

### Phase 4: 고급 기능 (진행 예정)
- [ ] 검색 (QueryDSL)
- [ ] 좋아요 기능
- [ ] 통계 API

## 🔌 API 엔드포인트

### 포스트 API

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/api/posts` | 포스트 목록 조회 | ✅ |
| GET | `/api/posts/{id}` | 포스트 상세 조회 | ✅ |
| POST | `/api/posts` | 포스트 생성 | ✅ |
| PUT | `/api/posts/{id}` | 포스트 수정 | ✅ |
| DELETE | `/api/posts/{id}` | 포스트 삭제 | ✅ |

### 예시 요청

#### 포스트 생성
```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot 시작하기",
    "content": "Spring Boot는 Java 기반의 강력한 프레임워크입니다.",
    "status": "PUBLISHED"
  }'
```

#### 포스트 목록 조회
```bash
curl "http://localhost:8080/api/posts?page=0&size=20&sort=createdAt,desc"
```

## 📁 프로젝트 구조

```
src/main/java/io/github/tato126/practice/
├── config/              # 설정 클래스
│   ├── JpaConfig.java
│   └── SecurityConfig.java
├── post/                # 포스트 도메인
│   ├── Post.java
│   ├── PostRepository.java
│   ├── PostService.java
│   ├── PostController.java
│   └── PostDto.java
├── user/                # 회원 도메인 (예정)
├── comment/             # 댓글 도메인 (예정)
├── tag/                 # 태그 도메인 (예정)
└── common/              # 공통 유틸리티
    └── GlobalExceptionHandler.java
```

## 🗄️ 데이터베이스 스키마

### posts 테이블

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | 기본키 (자동 증가) |
| title | VARCHAR(200) | 제목 |
| content | TEXT | 내용 |
| status | VARCHAR(20) | 상태 (DRAFT, PUBLISHED) |
| created_at | TIMESTAMP | 생성일시 |
| updated_at | TIMESTAMP | 수정일시 |

## ⚙️ 설정

### application.yml

주요 설정:
- **Server Port:** 8080
- **Database:** H2 In-Memory
- **JPA:** ddl-auto: create-drop (개발용)
- **Logging:** DEBUG (개발용)

### 환경별 설정 (예정)

- `application.yml` - 공통 설정
- `application-local.yml` - 로컬 개발
- `application-prod.yml` - 프로덕션

## 🧪 테스트

### 단위 테스트 (예정)
```bash
./gradlew test
```

### API 테스트

Swagger UI 또는 curl 사용

## 📚 참고 문서

- [RFP 문서](./RFP_BLOG_PLATFORM.md) - 프로젝트 요구사항
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

## 🔐 보안

### Phase 1 (현재)
- 모든 요청 허용 (개발 편의)

### Phase 2 (예정)
- JWT 토큰 인증
- BCrypt 비밀번호 암호화
- CSRF 설정

## 🐛 트러블슈팅

### 포트 8080 이미 사용 중
```yaml
# application.yml
server:
  port: 8081
```

### H2 Console 접속 안됨
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:devblog`
- Username: `sa`
- Password: (공백)

## 📝 개발 진행 상황

- [x] 프로젝트 초기 설정
- [x] 의존성 설정
- [x] Security 기본 설정
- [x] Phase 1: 포스트 CRUD (진행 중)
- [ ] Phase 2: 회원 & 인증
- [ ] Phase 3: 댓글 & 태그
- [ ] Phase 4: 고급 기능

## 👤 작성자

**tato126** & **U-hee**

## 📄 라이선스

This project is for learning purposes.
