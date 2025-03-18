package com.example.remind.post.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 게시물에 좋아요 추가하기
    @PostMapping
    public void postLike(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId) {
        postLikeService.postLike(authUser, postId);
    }

    // 게시물에 좋아요 취소하기
    @DeleteMapping("/likes")
    public void deletePostLike(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        postLikeService.deletePostLike(authUser, postId);
    }
}
