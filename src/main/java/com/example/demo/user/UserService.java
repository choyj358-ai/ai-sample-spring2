package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {
        // 아이디 중복 체크 (보안 및 데이터 정합성)
        userRepository.findByUsername(joinDTO.getUsername()).ifPresent(user -> {
            throw new RuntimeException("이미 사용중인 유저네임입니다");
        });

        // Entity 변환 및 저장 (비밀번호 암호화 적용)
        var user = User.builder()
                .username(joinDTO.getUsername())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .email(joinDTO.getEmail())
                .build();

        userRepository.save(user);
    }
}
