# 작업 보고서: T-2.2 로그인/로그아웃 기능 및 화면 구현

## 1. 개요
로그인 및 로그아웃 기능을 구현하고, 사용자 편의를 위한 UI 고도화 및 상태별 메뉴 분기 처리를 완료하였습니다.

## 2. 작업 내용

### Backend (Java)
- **`UserRequest.java`**: `Login` 내부 클래스에 `@NotBlank`, `@Size` 유효성 검사 어노테이션 추가.
- **`UserService.java`**: 
    - `login` 메서드 구현: 아이디 존재 여부 확인 및 `BCryptPasswordEncoder`를 이용한 비밀번호 검증.
    - 실패 시 통합 에러 처리를 위해 `RuntimeException` 발생.
- **`UserController.java`**:
    - `POST /login`: 로그인 요청 처리 및 `sessionUser` 세션 저장.
    - `GET /logout`: 세션 무효화(`invalidate`) 및 메인 페이지 리다이렉트.

### Frontend (Mustache)
- **`login-form.mustache`**: 
    - Bootstrap 5와 FontAwesome을 활용하여 모던하고 직관적인 디자인 적용.
    - 입력 필드 그룹화 및 시각적 피드백 강화.
- **`header.mustache`**: 
    - `{{#sessionUser}}`를 사용하여 로그인 여부에 따른 동적 내비게이션 구현.
    - `ms-auto`를 적용하여 메뉴 우측 정렬 및 레이아웃 개선.

## 3. 규칙 준수 여부
- **DTO 규칙 (6번)**: `UserRequest` 내부에 `Login` 클래스로 구현하여 명명 규칙 준수.
- **Service 규칙 (4번)**: 비밀번호 검증 및 엔티티 변환 로직을 Service 레이어에서 수행.
- **Controller 규칙 (5번)**: `HttpSession`을 이용한 SSR 인증 관리 및 리다이렉트 응답.

## 4. 향후 계획
- 로그인한 사용자만 접근 가능한 페이지(글쓰기 등)에 대한 권한 체크 인터셉터 구현 검토.
