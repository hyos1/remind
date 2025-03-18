package com.example.remind.comment.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 게시물 댓글 좋아요 추가
    @PostMapping("/{commentId}/likes")
    public void commentLike(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId) {
        commentLikeService.commentLike(authUser, postId, commentId);
    }

    // 게시물 댓글 좋아요 취소
    @DeleteMapping("/{commentId}/likes")
    public void deleteCommentLike(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId) {
        commentLikeService.deleteCommentLike(authUser, postId, commentId);
    }
}
