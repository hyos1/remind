package com.example.remind.user.repository;

import com.example.remind.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_사용자_조회o() {
        //given
        String email = "1234@naver,com";
        User user = new User("123", "123", email, "123", ",123");
        userRepository.save(user);
        //when
        User findUser = userRepository.findByEmail(email).orElse(null);

        //then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getEmail()).isEqualTo(email);
    }
}