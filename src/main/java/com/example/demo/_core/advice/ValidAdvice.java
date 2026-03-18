package com.example.demo._core.advice;

import com.example.demo._core.utils.Resp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@Aspect
@Component
public class ValidAdvice {

    // 모든 Controller 패키지 내의 모든 메서드 중 파라미터가 있는 메서드를 대상으로 설정
    @Pointcut("execution(* com.example.demo..*Controller.*(..))")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void validateAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            // 파라미터 중 Errors(또는 BindingResult)가 있는지 확인
            if (arg instanceof Errors errors) {
                if (errors.hasErrors()) {
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    // 첫 번째 에러 메시지를 응답으로 보냄 (프로젝트 컨벤션에 따라 다를 수 있음)
                    String errorMessage = fieldErrors.get(0).getDefaultMessage();
                    String field = fieldErrors.get(0).getField();
                    
                    // AOP에서 직접 응답을 반환할 수 없으므로 RuntimeException을 던지고 
                    // ExceptionHandler에서 처리하거나, 프로젝트 구조에 맞게 설계
                    // 여기서는 커스텀 예외를 던지는 방식으로 처리 (추후 ExceptionAdvice 추가 가능)
                    throw new RuntimeException(errorMessage);
                }
            }
        }
    }
}
