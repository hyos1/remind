package com.example.remind.user.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.common.config.PasswordEncoder;
import com.example.remind.user.dto.response.UserResponseDto;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void 아이디로_유저를_조회할_수_있다() {
        //given
        Long userId = 1L;
        String email = "1234@naver,com";
        String name = "123";
        AuthUser authUser = new AuthUser(userId, email);

        User user = new User(name, "123", email, "123", ",123");

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        //when
        UserResponseDto user1 = userService.findUser(authUser, userId);

        //then
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isEqualTo(userId);
    }

    @Test
    void 본인일_때_프라이빗_정보_조회o() {
        AuthUser authUser = new AuthUser(1L, "1234");

        String email = "1234@naver,com";
        String name = "123";
        User user = new User(name, "123", email, "123", ",123");


    }
}