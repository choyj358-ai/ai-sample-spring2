package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final com.example.demo.board.BoardRepository boardRepository;
    private final com.example.demo.reply.ReplyRepository replyRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다"));
    }

    @Transactional
    public User 회원수정(Integer id, UserRequest.UpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다"));

        user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        user.setEmail(updateDTO.getEmail());
        user.setPostcode(updateDTO.getPostcode());
        user.setAddress(updateDTO.getAddress());
        user.setDetailAddress(updateDTO.getDetailAddress());
        user.setExtraAddress(updateDTO.getExtraAddress());

        return user; // Dirty Checking
    }

    @Transactional
    public void 회원탈퇴(Integer id) {
        // 관련 데이터 일괄 삭제 (Reply -> Board -> User 순서 고려)
        replyRepository.deleteByUserId(id);
        boardRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public User login(UserRequest.Login loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다");
        }

        return user;
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
