# 📋 RFP: 개인 블로그 플랫폼 API

**프로젝트명:** DevBlog - 개발자 블로그 플랫폼
**작성일:** 2025-11-07
**예상 기간:** 4주 (Phase별 1주)

---

## 1. 프로젝트 개요

### 1.1 목적
개발자들이 기술 블로그를 작성하고 공유할 수 있는 RESTful API 개발

### 1.2 주요 기능
- 회원 가입/로그인 (JWT 인증)
- 블로그 포스트 작성/수정/삭제
- 댓글 작성
- 태그 기반 분류
- 포스트 검색/필터링
- 좋아요 기능

### 1.3 목표
- 실무 수준의 Spring Boot API 구현
- RESTful 설계 원칙 준수
- 적절한 예외 처리 및 검증
- 테스트 가능한 코드 구조

---

## 2. 기능 요구사항

### Phase 1: 기본 포스트 관리 (1주차)

#### FR-1.1 포스트 CRUD
- [ ] 포스트 목록 조회 (페이징, 정렬)
- [ ] 포스트 상세 조회
- [ ] 포스트 작성
- [ ] 포스트 수정
- [ ] 포스트 삭제

**포스트 정보:**
- 제목 (필수, 1-200자)
- 내용 (필수, 마크다운 지원)
- 작성자 (자동 설정)
- 공개 상태 (DRAFT, PUBLISHED)
- 생성일/수정일 (자동 관리)

**비즈니스 규칙:**
- 제목은 중복 가능
- 내용은 최소 10자 이상
- DRAFT 상태는 작성자만 조회 가능
- PUBLISHED 상태는 모두 조회 가능

#### FR-1.2 입력 검증
- [ ] 제목 길이 검증
- [ ] 내용 최소 길이 검증
- [ ] 공개 상태 ENUM 검증
- [ ] 적절한 에러 메시지 반환

---

### Phase 2: 회원 & 인증 (2주차)

#### FR-2.1 회원 관리
- [ ] 회원가입
- [ ] 로그인 (JWT 발급)
- [ ] 내 정보 조회
- [ ] 회원 탈퇴

**회원 정보:**
- 이메일 (필수, 유니크, 이메일 형식)
- 비밀번호 (필수, 8자 이상, BCrypt 암호화)
- 닉네임 (필수, 유니크, 2-20자)
- 소개 (선택, 500자 이하)
- 가입일 (자동)

**비즈니스 규칙:**
- 이메일 중복 불가
- 닉네임 중복 불가
- 비밀번호는 암호화 저장
- 탈퇴 시 포스트는 유지 (작성자 표시: "탈퇴한 사용자")

#### FR-2.2 JWT 인증
- [ ] Access Token 발급 (유효기간 1시간)
- [ ] Refresh Token 발급 (유효기간 7일)
- [ ] Token 재발급
- [ ] 인증 필터 적용

**보안 규칙:**
- 본인 포스트만 수정/삭제
- 인증된 사용자만 포스트 작성
- 비밀번호는 응답에 포함 금지

---

### Phase 3: 태그 & 댓글 (3주차)

#### FR-3.1 태그 시스템
- [ ] 포스트 작성 시 태그 추가 (최대 5개)
- [ ] 태그별 포스트 조회
- [ ] 인기 태그 조회 (포스트 수 기준 상위 20개)

**태그 정보:**
- 이름 (필수, 유니크, 1-20자, 한글/영문/숫자)
- 포스트 수 (자동 계산)

**비즈니스 규칙:**
- 태그는 대소문자 구분 없음 (모두 소문자 저장)
- 공백 제거 (예: "Spring Boot" → "springboot")
- 자동 생성 (포스트 작성 시 없으면 생성)

#### FR-3.2 댓글 시스템
- [ ] 댓글 작성
- [ ] 댓글 수정
- [ ] 댓글 삭제
- [ ] 포스트별 댓글 조회

**댓글 정보:**
- 내용 (필수, 1-500자)
- 작성자
- 포스트 ID
- 생성일/수정일

**비즈니스 규칙:**
- 인증된 사용자만 댓글 작성
- 본인 댓글만 수정/삭제
- 포스트 삭제 시 댓글도 삭제 (CASCADE)
- 댓글 삭제 시 "삭제된 댓글입니다" 표시 (Soft Delete)

---

### Phase 4: 고급 기능 (4주차)

#### FR-4.1 검색 & 필터링
- [ ] 제목/내용으로 검색
- [ ] 작성자로 필터링
- [ ] 태그로 필터링 (다중 선택 가능)
- [ ] 공개 상태로 필터링
- [ ] 날짜 범위 검색

**검색 조건:**
- 키워드는 제목과 내용 모두 검색 (OR 조건)
- 태그는 AND 조건 (모든 태그 포함)
- 정렬 옵션: 최신순, 오래된순, 댓글 많은 순

#### FR-4.2 좋아요 기능
- [ ] 포스트 좋아요 추가
- [ ] 포스트 좋아요 취소
- [ ] 내가 좋아요한 포스트 목록
- [ ] 포스트별 좋아요 수 조회

**비즈니스 규칙:**
- 인증된 사용자만 좋아요 가능
- 중복 좋아요 방지
- 본인 포스트도 좋아요 가능

#### FR-4.3 통계
- [ ] 내 포스트 통계 (총 포스트 수, 총 댓글 수, 총 좋아요 수)
- [ ] 인기 포스트 (좋아요 기준 상위 10개)
- [ ] 최근 활동 (최근 7일 내 작성된 포스트)

---

## 3. 비기능 요구사항

### NFR-1 성능
- [ ] 페이징 적용 (기본 20개, 최대 100개)
- [ ] N+1 문제 방지 (Fetch Join 사용)
- [ ] 인덱스 적용 (이메일, 태그명, 생성일)

### NFR-2 보안
- [ ] SQL Injection 방지 (PreparedStatement)
- [ ] XSS 방지 (입력값 검증)
- [ ] CSRF 설정 (필요 시)
- [ ] 비밀번호 평문 저장 금지

### NFR-3 코드 품질
- [ ] 계층 분리 (Controller, Service, Repository)
- [ ] DTO 사용 (Entity 직접 노출 금지)
- [ ] 예외 처리 (@RestControllerAdvice)
- [ ] 로깅 (주요 동작 로그 남기기)

### NFR-4 테스트
- [ ] 단위 테스트 (Service 계층)
- [ ] 통합 테스트 (API 계층)
- [ ] Repository 테스트

---

## 4. 기술 스펙

### 4.1 필수 기술
- **Backend:** Spring Boot 3.5+
- **Language:** Java 21
- **Database:** H2 (개발), PostgreSQL/MySQL (선택)
- **Security:** Spring Security + JWT
- **ORM:** Spring Data JPA
- **Build:** Gradle

### 4.2 권장 라이브러리
- Lombok (보일러플레이트 감소)
- Validation (입력 검증)
- QueryDSL (복잡한 쿼리)
- SpringDoc OpenAPI (API 문서)

---

## 5. API 명세

### 5.1 인증

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/signup` | 회원가입 | No |
| POST | `/api/auth/login` | 로그인 | No |
| POST | `/api/auth/refresh` | 토큰 재발급 | No |
| GET | `/api/auth/me` | 내 정보 조회 | Yes |
| DELETE | `/api/auth/me` | 회원 탈퇴 | Yes |

**Request: 회원가입**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "nickname": "devblogger",
  "bio": "백엔드 개발자입니다"
}
```

**Response: 로그인**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "devblogger",
    "bio": "백엔드 개발자입니다"
  }
}
```

### 5.2 포스트

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/posts` | 포스트 목록 | No |
| GET | `/api/posts/{id}` | 포스트 상세 | No |
| POST | `/api/posts` | 포스트 작성 | Yes |
| PUT | `/api/posts/{id}` | 포스트 수정 | Yes |
| DELETE | `/api/posts/{id}` | 포스트 삭제 | Yes |
| GET | `/api/posts/search` | 포스트 검색 | No |

**Request: 포스트 작성**
```json
{
  "title": "스프링부트 시작하기",
  "content": "# 스프링부트란?\n\n스프링부트는...",
  "status": "PUBLISHED",
  "tags": ["spring", "java", "backend"]
}
```

**Response: 포스트 목록**
```json
{
  "content": [
    {
      "id": 1,
      "title": "스프링부트 시작하기",
      "content": "# 스프링부트란?...",
      "status": "PUBLISHED",
      "author": {
        "id": 1,
        "nickname": "devblogger"
      },
      "tags": ["spring", "java", "backend"],
      "commentCount": 5,
      "likeCount": 10,
      "createdAt": "2025-11-07T10:00:00",
      "updatedAt": "2025-11-07T10:30:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "totalElements": 50,
    "totalPages": 3
  }
}
```

**Query Parameters:**
```
GET /api/posts?page=0&size=20&sort=createdAt,desc
GET /api/posts?status=PUBLISHED
GET /api/posts/search?keyword=스프링&tags=java,spring
```

### 5.3 댓글

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/posts/{postId}/comments` | 댓글 목록 | No |
| POST | `/api/posts/{postId}/comments` | 댓글 작성 | Yes |
| PUT | `/api/comments/{id}` | 댓글 수정 | Yes |
| DELETE | `/api/comments/{id}` | 댓글 삭제 | Yes |

**Request: 댓글 작성**
```json
{
  "content": "좋은 글 감사합니다!"
}
```

**Response: 댓글 목록**
```json
{
  "content": [
    {
      "id": 1,
      "content": "좋은 글 감사합니다!",
      "author": {
        "id": 2,
        "nickname": "reader"
      },
      "createdAt": "2025-11-07T11:00:00",
      "updatedAt": "2025-11-07T11:00:00",
      "isDeleted": false
    }
  ],
  "totalElements": 5
}
```

### 5.4 태그

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/tags` | 인기 태그 목록 | No |
| GET | `/api/tags/{name}/posts` | 태그별 포스트 | No |

### 5.5 좋아요

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/posts/{postId}/like` | 좋아요 추가 | Yes |
| DELETE | `/api/posts/{postId}/like` | 좋아요 취소 | Yes |
| GET | `/api/users/me/liked-posts` | 내가 좋아요한 포스트 | Yes |

---

## 6. 데이터 모델

### 6.1 ERD

```
User (회원)
  ├─ 1:N → Post (포스트)
  ├─ 1:N → Comment (댓글)
  └─ N:M → Post (좋아요)

Post (포스트)
  ├─ N:1 → User (작성자)
  ├─ 1:N → Comment (댓글)
  ├─ N:M → Tag (태그)
  └─ N:M → User (좋아요)

Comment (댓글)
  ├─ N:1 → User (작성자)
  └─ N:1 → Post (포스트)

Tag (태그)
  └─ N:M → Post (포스트)
```

### 6.2 테이블 스키마

**users**
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50) NOT NULL UNIQUE,
  bio VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  INDEX idx_email (email),
  INDEX idx_nickname (nickname)
);
```

**posts**
```sql
CREATE TABLE posts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(200) NOT NULL,
  content TEXT NOT NULL,
  status VARCHAR(20) NOT NULL, -- DRAFT, PUBLISHED
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX idx_user_id (user_id),
  INDEX idx_status (status),
  INDEX idx_created_at (created_at)
);
```

**comments**
```sql
CREATE TABLE comments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  content VARCHAR(500) NOT NULL,
  user_id BIGINT NOT NULL,
  post_id BIGINT NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  INDEX idx_post_id (post_id)
);
```

**tags**
```sql
CREATE TABLE tags (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  INDEX idx_name (name)
);
```

**post_tags**
```sql
CREATE TABLE post_tags (
  post_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (post_id, tag_id),
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);
```

**post_likes**
```sql
CREATE TABLE post_likes (
  user_id BIGINT NOT NULL,
  post_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, post_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);
```

---

## 7. 구현 우선순위

### Week 1: 기본 구조 + 포스트 CRUD
```
Day 1-2: 프로젝트 설정, Entity/Repository 구현
Day 3-4: Post CRUD API 구현
Day 5: 검증 & 예외 처리
Day 6-7: 테스트 작성
```

### Week 2: 회원 & 인증
```
Day 1-2: User Entity, 회원가입 API
Day 3-4: JWT 구현, 로그인 API
Day 5: Security Filter 적용
Day 6-7: 권한 검증, 테스트
```

### Week 3: 태그 & 댓글
```
Day 1-2: Tag Entity, 태그 시스템
Day 3-4: Comment Entity, 댓글 API
Day 5-7: 통합 테스트, 리팩토링
```

### Week 4: 고급 기능 & 완성
```
Day 1-2: 검색 기능 (QueryDSL)
Day 3-4: 좋아요 기능, 통계
Day 5-7: 성능 최적화, 문서화
```

---

## 8. 평가 기준

### 필수 (80점)
- [ ] Phase 1-2 완료 (포스트 + 인증)
- [ ] RESTful API 설계
- [ ] 적절한 HTTP 상태 코드
- [ ] 입력 검증 & 예외 처리
- [ ] JWT 인증 구현
- [ ] 계층 분리 (Controller, Service, Repository)

### 가산점 (20점)
- [ ] Phase 3-4 완료 (댓글, 태그, 검색, 좋아요)
- [ ] 테스트 코드 작성
- [ ] API 문서화 (Swagger/SpringDoc)
- [ ] QueryDSL 사용
- [ ] 성능 최적화 (N+1 해결, 인덱스)
- [ ] Docker 배포 환경 구성

---

## 9. 제출물

### 9.1 필수
- [ ] 소스 코드 (GitHub Repository)
- [ ] README.md (실행 방법, API 문서 링크)
- [ ] API 테스트 결과 (Postman Collection 또는 스크린샷)

### 9.2 선택
- [ ] ERD 다이어그램
- [ ] 아키텍처 문서
- [ ] 테스트 커버리지 리포트
- [ ] 성능 테스트 결과

---

## 10. 참고 자료

### API 설계
- RESTful API 가이드
- HTTP 상태 코드 가이드
- JWT 모범 사례

### Spring Boot
- Spring Boot Reference Documentation
- Spring Data JPA Reference
- Spring Security Reference

---

## 📌 빠른 시작 체크리스트

### Phase 1 시작 전 확인
- [ ] `build.gradle`에 JPA, H2 추가
- [ ] `application.yml` 설정
- [ ] 패키지 구조 생성 (`user`, `post`, `comment`, `tag`, `common`)
- [ ] Security 임시 비활성화 (Phase 2 전까지)

### Phase 1 완료 기준
- [ ] 포스트 CRUD API 모두 작동
- [ ] Postman/curl로 테스트 성공
- [ ] 검증 에러 처리 확인
- [ ] H2 Console에서 데이터 확인

---

*실무처럼 만들고, 프로덕션 수준으로 완성하기!*
