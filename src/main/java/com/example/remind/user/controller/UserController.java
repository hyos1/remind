package com.example.remind.user.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.user.dto.request.UserPasswordRequestDto;
import com.example.remind.user.dto.request.UserRequestDto;
import com.example.remind.user.dto.request.UserUpdatePasswordRequestDto;
import com.example.remind.user.dto.request.UserUpdateRequestDto;
import com.example.remind.user.dto.response.UserResponseDto;
import com.example.remind.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //유저 단건 조회 원래 로직
    @GetMapping("/{userId}")
    public UserResponseDto findUser(@Auth AuthUser authUser, @PathVariable(name = "userId") Long userId) {
        return userService.findUser(authUser, userId);
    }

//    //유저 단건 조회(이름으로)
//    @GetMapping("/{userName}")
//    public UserResponseDto findUser(@Auth AuthUser authUser, @PathVariable(name = "userName") String userName) {
//        return userService.findUser(authUser, userName);
//    }


//    //유저 정보 수정 (비밀번호 제외)
//    @PatchMapping("/{userId}")
//    public void update(
//            @PathVariable Long userId,
//            @Auth AuthUser authUser,
//            @RequestBody UserUpdateRequestDto dto) {
//        userService.update(userId, authUser, dto);
//    }

    //유저 정보 수정 (비밀번호 제외) 원래거
    @PatchMapping
    public void update(
            @Auth AuthUser authUser,
            @RequestBody UserUpdateRequestDto dto) {
        userService.update(authUser, dto);
    }

    //비밀번호만! 수정
    @PatchMapping("/password")
    public void updatePassword(
            @Auth AuthUser authUser,
            @RequestBody UserUpdatePasswordRequestDto dto) {
        userService.updatePassword(authUser, dto);
    }

    @DeleteMapping
    public void deleteUser(@Auth AuthUser authUser, @RequestBody UserPasswordRequestDto dto) {
        userService.deleteUser(authUser, dto);
    }
}
