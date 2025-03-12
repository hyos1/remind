package com.example.remind.auth.dto.request;

import lombok.Getter;

@Getter
public class AuthRequestDto {

    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phoneNum;
}
