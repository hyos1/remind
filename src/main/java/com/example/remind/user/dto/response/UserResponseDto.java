package com.example.remind.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String name;
    private final String nickname;

    public UserResponseDto(Long id, String name, String nickname) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }
}
