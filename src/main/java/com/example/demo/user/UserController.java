package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm(org.springframework.ui.Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        User user = userService.회원정보보기(sessionUser.getId());
        model.addAttribute("user", user);
        return "user/update-form";
    }

    @PostMapping("/user/update")
    public String update(@jakarta.validation.Valid UserRequest.UpdateDTO updateDTO, org.springframework.validation.Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        User user = userService.회원수정(sessionUser.getId(), updateDTO);
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    @PostMapping("/user/withdraw")
    public String withdraw() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        userService.회원탈퇴(sessionUser.getId());
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@jakarta.validation.Valid UserRequest.Login loginRequest, org.springframework.validation.Errors errors) {
        User sessionUser = userService.login(loginRequest);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@jakarta.validation.Valid UserRequest.JoinDTO joinDTO, org.springframework.validation.Errors errors) {
        userService.join(joinDTO);
        return "redirect:/login-form";
    }

}
