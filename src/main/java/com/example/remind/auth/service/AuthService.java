package com.example.remind.auth.service;

import com.example.remind.auth.dto.request.AuthRequestDto;
import com.example.remind.auth.dto.response.AuthResponseDto;
import com.example.remind.auth.dto.request.AuthLoginRequestDto;
import com.example.remind.common.config.JwtUtil;
import com.example.remind.common.config.PasswordEncoder;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(AuthRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        //새로운 이메일은 회원가입 가능
        User user = new User(dto.getName(), dto.getNickname(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getPhoneNum());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(AuthLoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new IllegalStateException("해당 아이디가 존재하지 않습니다.")
        );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail());
        return new AuthResponseDto(bearerToken);
    }
}
