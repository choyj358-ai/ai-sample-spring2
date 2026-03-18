# 🚩 작업 보고서: 회원가입 시스템 구현 (T-2.1)

- **작업 일시**: 2026-03-18
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **보안 설정 (SecurityConfig)**: 비밀번호 암호화를 위한 `BCryptPasswordEncoder` 빈을 등록하였습니다.
2. **서비스 로직 고도화 (UserService)**: `join` 메서드에서 아이디 중복 여부를 최종 확인하고, 비밀번호를 암호화하여 저장하도록 수정하였습니다.
3. **컨트롤러 구현 (UserController)**: 표준 Form(POST) 방식을 사용하여 회원가입 요청을 처리하고, 성공 시 로그인 페이지로 리다이렉트되도록 구현하였습니다.
4. **글로벌 예외 처리 (MyExceptionAdvice)**: 유효성 검사 실패나 중복 아이디 발생 시, 사용자에게 `alert` 창을 띄우고 이전 페이지로 돌려보내는 스크립트 응답 기능을 추가하였습니다.
5. **화면 연동 (join-form.mustache)**: 기존 AJAX 방식에서 표준 `form` 태그 방식으로 전환하되, 아이디 중복 체크는 사용자 경험을 위해 비동기(AJAX)로 유지하였습니다.
6. **검증 (Unit Test)**: `UserApiControllerTest`를 Form 방식 테스트로 전환하여 모든 시나리오(성공, 유효성 실패)가 통과함을 확인하였습니다.

## 2. 🧩 변경된 핵심 코드

### SecurityConfig.java (암호화 설정)
```java
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 안전하게 해시화하는 도구 등록
    }
}
```

### UserService.java (비즈니스 로직)
```java
@Transactional
public void join(UserRequest.JoinDTO joinDTO) {
    // 1. 아이디 중복 체크 (방어적 코드)
    userRepository.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
        throw new RuntimeException("이미 사용중인 유저네임입니다");
    });

    // 2. 비밀번호 암호화 및 저장
    var user = User.builder()
            .username(joinDTO.getUsername())
            .password(passwordEncoder.encode(joinDTO.getPassword())) // 암호화 적용!
            .email(joinDTO.getEmail())
            .build();

    userRepository.save(user);
}
```

### MyExceptionAdvice.java (공통 에러 응답)
```java
@ExceptionHandler(RuntimeException.class)
public ResponseEntity<?> onRuntimeException(RuntimeException e, HttpServletRequest request) {
    String path = request.getRequestURI();
    
    if (path.startsWith("/api")) {
        return Resp.fail(HttpStatus.BAD_REQUEST, e.getMessage()); // API는 JSON 응답
    } else {
        // 일반 페이지 요청은 스크립트로 alert 창 띄우기
        String script = """
                <script>
                    alert("%s");
                    history.back();
                </script>
                """.formatted(e.getMessage());
        return new ResponseEntity<>(script, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
    }
}
```

## 3. 🍦 상세비유 (Easy Analogy)

"이번 작업은 **신규 회원 전용 보안 출입증 발급**과 같습니다!"

1. **중복 체크**: 안내 데스크(AJAX)에서 미리 "이 이름으로 발급된 카드가 있나요?"라고 물어봅니다.
2. **암호화**: 회원이 적어준 비밀번호를 그대로 장부에 적지 않고, 특수 장비(BCrypt)를 이용해 아무도 알아볼 수 없는 난수로 변환하여 금고(DB)에 넣습니다.
3. **에러 처리**: 만약 서류 양식이 틀렸거나 문제가 생기면, 안내원이 "다시 작성해 주세요"라고 친절하게 쪽지(Alert)를 건네주며 이전 단계로 안내하는 것과 비슷합니다.

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **BCrypt 암호화**: 단순히 비밀번호를 숨기는 것이 아니라, '솔팅(Salting)' 기법을 사용하여 매번 다른 해시값을 생성합니다. 이를 통해 레인보우 테이블 공격 등으로부터 사용자 데이터를 안전하게 보호합니다.
- **Spring Validation & AOP**: 컨트롤러 진입 전 `@Valid` 어노테이션을 통해 데이터 형식을 검사하고, `ValidAdvice`(AOP)가 에러 발생 시 즉시 예외를 던짐으로써 비즈니스 로직과 검증 로직을 깔끔하게 분리하였습니다.
- **Accept-Header/Path 기반 예외 처리**: 동일한 서버에서 AJAX 요청과 일반 페이지 요청을 모두 처리할 때, 응답 형식을(JSON vs HTML/Script) 상황에 맞게 동적으로 결정하는 전략을 사용하였습니다.
