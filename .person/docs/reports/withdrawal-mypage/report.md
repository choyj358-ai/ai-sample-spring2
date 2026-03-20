# 회원 탈퇴 및 마이페이지 기능 구현 보고서

## 1. 개요
회원 정보 수정(마이페이지) 및 회원 탈퇴 기능을 구현하여 사용자의 자기 정보 관리 권한을 강화함.

## 2. 주요 변경 사항

### 2.1 Backend (Java/Spring)
- **`UserRequest.java`**: `UpdateDTO` 추가 (비밀번호, 이메일, 주소 필드 포함)
- **`UserService.java`**:
  - `회원정보보기(Integer id)`: 사용자 정보 조회
  - `회원수정(Integer id, UpdateDTO updateDTO)`: Dirty Checking을 활용한 정보 업데이트 및 비밀번호 암호화 처리
  - `회원탈퇴(Integer id)`: 관련 게시글 및 댓글 일괄 삭제(Cascade) 후 회원 정보 삭제
- **`UserController.java`**:
  - `GET /user/update-form`: 수정 폼 매핑 및 세션 체크
  - `POST /user/update`: 수정 처리 및 세션 갱신
  - `POST /user/withdraw`: 탈퇴 처리 및 세션 무효화
- **`BoardRepository`, `ReplyRepository`**: `deleteByUserId(Integer userId)` 메서드 추가

### 2.2 Frontend (Mustache/Bootstrap)
- **`update-form.mustache`**:
  - 사용자 정보를 기본값으로 채운 수정 폼 구현
  - 다음 주소 API(Daum Postcode) 연동을 통한 주소 입력 기능 추가
  - 하단에 회원 탈퇴 링크 및 확인용 `confirm()` 스크립트 추가
- **`header.mustache`**: 로그인 상태 시 '회원정보수정' 링크가 `/user/update-form`을 가리키도록 설정

## 3. 규칙 준수 여부
- **메서드명 한글화**: `UserService` 내 메서드명을 `회원정보보기`, `회원수정`, `회원탈퇴`로 구현 완료
- **HTTP 메서드**: GET과 POST만 사용하여 모든 기능 구현
- **보안**: 비밀번호 수정 시 `PasswordEncoder`를 통한 암호화 적용
- **레이아웃**: 기존 `header`, `footer` 레이아웃을 유지하며 디자인 통일성 확보

## 4. 향후 계획
- [ ] 게시글 목록 조회 및 작성 기능 연동 확인 (T-3.x)
- [ ] 탈퇴 시 게시글 삭제 대신 작성자를 '탈퇴회원'으로 변경하는 Soft delete 전략 고려 가능
