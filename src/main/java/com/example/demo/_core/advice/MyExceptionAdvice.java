package com.example.demo._core.advice;

import com.example.demo._core.utils.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionAdvice {

    // 모든 RuntimeException을 가로채서 에러 메시지를 응답
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> onRuntimeException(RuntimeException e, jakarta.servlet.http.HttpServletRequest request) {
        String path = request.getRequestURI();
        
        if (path.startsWith("/api")) {
            return Resp.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        } else {
            String script = """
                    <script>
                        alert("%s");
                        history.back();
                    </script>
                    """.formatted(e.getMessage());
            return new ResponseEntity<>(script, org.springframework.http.HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
        }
    }
}
