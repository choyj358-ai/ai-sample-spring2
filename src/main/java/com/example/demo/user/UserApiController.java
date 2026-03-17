package com.example.demo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo._core.utils.Resp;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<Resp<Boolean>> usernameSameCheck(String username) {
        boolean isSame = userService.checkUsername(username);
        return Resp.ok(isSame);
    }

}
