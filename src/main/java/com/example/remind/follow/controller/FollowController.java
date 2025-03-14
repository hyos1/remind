package com.example.remind.follow.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.follow.service.FollowService;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.user.dto.response.UserResponseDto;
import com.example.remind.user.repository.UserRepository;
import com.example.remind.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;


    /**
     * 팔로우 신청
     */
    @PostMapping("/{friendId}")
    public void follow(@PathVariable(name = "friendId") Long friendId, @Auth AuthUser authUser) {
        followService.follow(friendId, authUser.getId());
    }

    // 내가 팔로우 하는 사람의 최신 게시물들 최신순으로 조회
    @GetMapping("/{friendId}/posts")
    public Page<PostResponseDto> getUsersPosts(
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @PathVariable(name = "friendId") Long friendId,
            @Auth AuthUser authUser) {
        return followService.getUserPosts(pageNum, pageSize, friendId, authUser);
    }


    //언팔로우 기능 추가
}
