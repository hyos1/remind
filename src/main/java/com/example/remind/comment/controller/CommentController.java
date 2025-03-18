package com.example.remind.comment.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.comment.dto.request.CommentRequestDto;
import com.example.remind.comment.dto.request.CommentUpdateRequestDto;
import com.example.remind.comment.dto.response.CommentResponseDto;
import com.example.remind.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public CommentResponseDto createComment(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId,
            @RequestBody CommentRequestDto dto) {
        return commentService.createComment(authUser, postId, dto);
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentUpdateRequestDto dto
    ) {
        return commentService.updateComment(authUser, postId, commentId, dto);
    }

    // 댓글 삭제
    @DeleteMapping({"/{commentId}"})
    public void deleteComment(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            @Auth AuthUser authUser
    ) {
        commentService.deleteComment(postId, commentId, authUser);
    }
}
