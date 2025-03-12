package com.example.remind.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private String name;
    private String nickname;
    private String email;
    private String phoneNum;
    private String password;
}
