# 기능 명세서 (Functional Specification)

## 1. 개요
본 문서는 "심플 다중 사용자 블로그" 프로젝트의 세부 기능과 동작 방식을 정의합니다. `prd.md`의 요구사항을 기반으로 구현 수준의 명세를 포함합니다.

---

## 2. 회원 관리 (User Management)

### 2.1 회원가입
- **Endpoint**: `POST /api/users/join`
- **입력**: `username` (아이디), `password` (비밀번호), `email` (이메일)
- **제약**: 
    - `username`은 중복될 수 없다.
    - 비밀번호는 암호화되어 저장되어야 한다.
- **결과**: 가입 성공 시 사용자 정보 반환 (비밀번호 제외).

### 2.2 로그인 (JWT 인증)
- **Endpoint**: `POST /api/users/login`
- **입력**: `username`, `password`
- **결과**: 인증 성공 시 `Authorization` 헤더에 JWT 토큰 발급.
- **권한**: 토큰이 없는 상태(비회원)에서도 시도 가능.

### 2.3 회원 탈퇴
- **Endpoint**: `DELETE /api/users/me`
- **권한**: 로그인한 본인만 가능.
- **동작**: 
    - 사용자의 계정 상태를 탈퇴로 변경 또는 삭제.
    - 해당 사용자가 작성한 모든 게시글과 댓글은 목록 및 상세 조회에서 비노출 처리되어야 한다. (Soft Delete 또는 Cascade 처리)

---

## 3. 게시글 관리 (Post Management)

### 3.1 게시글 목록 조회
- **Endpoint**: `GET /api/boards`
- **파라미터**: `page` (기본값 0), `size` (기본값 10), `keyword` (검색어)
- **동작**:
    - 모든 게시글을 최신순(`id` DESC)으로 조회.
    - `keyword`가 제공될 경우 제목(`title`) 또는 본문(`content`)에 키워드가 포함된 글만 필터링.
    - 페이징 정보(현재 페이지, 총 페이지 수 등)를 포함하여 응답.
- **권한**: 누구나(비회원 포함) 가능.

### 3.2 게시글 상세 조회
- **Endpoint**: `GET /api/boards/{id}`
- **동작**:
    - 특정 ID의 게시글 정보와 해당 게시글에 달린 댓글 목록을 함께 조회.
- **권한**: 누구나(비회원 포함) 가능.

### 3.3 게시글 작성
- **Endpoint**: `POST /api/boards`
- **입력**: `title`, `content`
- **동작**: 로그인한 사용자의 정보를 작성자로 설정하여 저장.
- **권한**: 로그인한 회원만 가능.

### 3.4 게시글 수정
- **Endpoint**: `PUT /api/boards/{id}`
- **입력**: `title`, `content`
- **권한**: 작성자 본인만 가능.

### 3.5 게시글 삭제
- **Endpoint**: `DELETE /api/boards/{id}`
- **권한**: 작성자 본인만 가능.

---

## 4. 댓글 관리 (Comment Management)

### 4.1 댓글 작성
- **Endpoint**: `POST /api/replies`
- **입력**: `boardId` (대상 게시글 ID), `content`
- **동작**: 1단 구조로 게시글에 직접 귀속.
- **권한**: 로그인한 회원만 가능.

### 4.2 댓글 삭제
- **Endpoint**: `DELETE /api/replies/{id}`
- **권한**: 작성자 본인만 가능.

---

## 5. 공통 시스템 명세

### 5.1 응답 표준 (`Resp<T>`)
- 모든 API 응답은 일관된 구조를 유지한다.
- `{ "status": 200, "msg": "성공", "body": { ... } }`

### 5.2 예외 처리
- 권한 없는 접근 시 403 Forbidden 반환.
- 존재하지 않는 리소스 접근 시 404 Not Found 반환.
- 잘못된 입력값 전송 시 400 Bad Request 반환.

### 5.3 보안
- 비밀번호 암호화: BCryptPasswordEncoder 사용.
- 인증 방식: Stateless JWT (Bearer Token).
