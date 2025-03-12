package com.example.remind.user.dto.response;

import lombok.Getter;

@Getter
public class UserPrivateResponseDto extends UserResponseDto {

    private final String email;
    private final String phoneNum;

    public UserPrivateResponseDto(Long id, String name, String nickname, String email, String phoneNum) {
        super(id, name, nickname);
        this.email = email;
        this.phoneNum = phoneNum;
    }
}
