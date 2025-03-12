package com.example.remind.user.dto.response;

import lombok.Getter;

@Getter
public class UserPublicResponseDto extends UserResponseDto {

    private final String email;

    public UserPublicResponseDto(Long id, String name, String nickname, String email) {
        super(id, name, nickname);
        this.email = email;
    }
}
