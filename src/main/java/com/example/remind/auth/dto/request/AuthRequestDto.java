package com.example.remind.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AuthRequestDto {

    private String name;
    private String nickname;
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "올바른 이메일 형식이어야 합니다."
    )
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다."
    )
    private String password;
    private String phoneNum;
}
