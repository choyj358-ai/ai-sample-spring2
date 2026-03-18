# 🚩 작업 보고서: Spring Boot 4.0.3 기반 AOP 유효성 검사 구현

- **작업 일시**: 2026-03-18
- **진행 단계**: 완료 및 IDE 검증 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **Spring Boot 4.0.3 마이그레이션**: 프로젝트를 최신 안정 버전인 4.0.3으로 업그레이드하고, 이에 따른 모듈화된 의존성(`spring-boot-starter-webmvc`, `spring-boot-starter-aspectj`)을 재설정하였습니다.
2. **DTO 설계 및 유효성 제약 조건**: `UserRequest.JoinDTO`에 Jakarta Validation(`@NotBlank`, `@Size`, `@Email`)을 적용하여 데이터 정합성 규칙을 정의했습니다.
3. **AOP를 활용한 공통 검증**: `ValidAdvice`를 구현하여 컨트롤러 메서드 실행 전 `BindingResult`를 자동으로 검사하고, 에러 발견 시 즉시 예외를 던지도록 설계했습니다.
4. **글로벌 예외 처리(Advice)**: 던져진 예외를 `MyExceptionAdvice`에서 가로채어 `Resp.fail` 형식의 표준 JSON 응답을 반환하도록 통일했습니다.
5. **테스트 환경 구축 (Spring Boot 4.0 최적화)**: Jackson 3(`tools.jackson`) 및 신규 테스트 스타터(`spring-boot-starter-webmvc-test`)를 사용하여 검증용 테스트 코드를 작성하고 IDE 연동까지 완료했습니다.

## 2. 🧩 핵심 코드 (Core Logic)

### UserApiController (Validation 적용)
```java
@PostMapping("/api/user/join")
public ResponseEntity<Resp<Object>> join(@RequestBody @Valid UserRequest.JoinDTO joinDTO, Errors errors) {
    // AOP(ValidAdvice)가 입구에서 에러를 컷트함!
    userService.join(joinDTO);
    return Resp.ok(null);
}
```

### ValidAdvice (AOP)
```java
@Aspect
@Component
public class ValidAdvice {
    @Before("execution(* com.example.demo..*Controller.*(..))")
    public void validateAdvice(JoinPoint jp) {
        for (Object arg : jp.getArgs()) {
            if (arg instanceof Errors errors && errors.hasErrors()) {
                // 첫 번째 에러 메시지를 즉시 예외로 던짐
                throw new RuntimeException(errors.getFieldErrors().get(0).getDefaultMessage());
            }
        }
    }
}
```

## 3. 🍦 상세비유 (Easy Analogy)

이번 작업은 **"공항의 자동 출입국 심사대"**를 설치한 것과 같습니다.
- 예전에는 기장(컨트롤러)이 비행기를 띄우기 직전에 승객의 여권(데이터)을 일일이 확인해야 했습니다.
- 이제는 **자동 심사대(AOP)**가 탑승구 앞에서 여권이 유효한지(Validation) 미리 검사합니다.
- 여권이 잘못된 승객은 심사대에서 즉시 돌려보내고(에러 응답), 기장은 오직 비행(비즈니스 로직)에만 집중할 수 있게 되었습니다.

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **Spring Boot 4.0 Modularization**: Spring Boot 4는 테스트 성능 향상을 위해 스타터를 모듈화했습니다. `MockMvc` 사용을 위해 `spring-boot-starter-webmvc-test`가 필수입니다.
- **Jackson 3 (tools.jackson)**: 기존 `com.fasterxml.jackson`에서 `tools.jackson`으로 네임스페이스가 변경되어, 더 빠르고 강력한 JSON 직렬화를 지원합니다.
- **Aspect-Oriented Programming (AOP)**: 비즈니스 로직(회원가입)과 공통 관심사(유효성 검증)를 분리하여 코드의 가독성과 유지보수성을 극대화했습니다.
