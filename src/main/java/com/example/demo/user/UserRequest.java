package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {
        @NotBlank(message = "유저네임은 필수입니다")
        @Size(min = 4, max = 20, message = "유저네임은 4~20자 이내여야 합니다")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내여야 합니다")
        private String password;

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "이메일 형식이 올바르지 않습니다")
        private String email;
    }

    @Data
    public static class Login {
        @NotBlank(message = "유저네임은 필수입니다")
        @Size(min = 4, max = 20, message = "유저네임은 4~20자 이내여야 합니다")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내여야 합니다")
        private String password;
    }

    @Data
    public static class UpdateDTO {
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내여야 합니다")
        private String password;

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "이메일 형식이 올바르지 않습니다")
        private String email;

        private String postcode;
        private String address;
        private String detailAddress;
        private String extraAddress;
    }

}
