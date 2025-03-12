package com.example.remind.user.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.common.config.PasswordEncoder;
import com.example.remind.user.dto.request.UserPasswordRequestDto;
import com.example.remind.user.dto.request.UserUpdatePasswordRequestDto;
import com.example.remind.user.dto.request.UserUpdateRequestDto;
import com.example.remind.user.dto.response.UserPrivateResponseDto;
import com.example.remind.user.dto.response.UserPublicResponseDto;
import com.example.remind.user.dto.response.UserResponseDto;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 단건 조회
    @Transactional(readOnly = true)
    public UserResponseDto findUser(AuthUser authUser, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 사용자가 존재하지 않습니다.")
        );

        if (user.getEmail().equals(authUser.getEmail())) {
            return new UserPrivateResponseDto(user.getId(), user.getName(), user.getNickname(), user.getEmail(), user.getPhoneNum());
        }

        return new UserPublicResponseDto(user.getId(), user.getName(), user.getNickname(), user.getEmail());

    }

    @Transactional
    public void update(AuthUser authUser, UserUpdateRequestDto dto) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("사용 중인 이메일입니다.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        user.update(dto.getName(), dto.getNickname(), dto.getEmail(), dto.getPhoneNum());
        userRepository.save(user);

    }

    @Transactional
    public void updatePassword(AuthUser authUser, UserUpdatePasswordRequestDto dto) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("같은 비밀번호로 수정할 수 없습니다.");
        }

        user.updatePassword(dto.getPassword());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(AuthUser authUser, UserPasswordRequestDto dto) {

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 맞지 않습니다.");
        }

        userRepository.deleteById(user.getId());
    }
}
