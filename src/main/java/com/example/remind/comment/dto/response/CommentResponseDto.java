package com.example.remind.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String comment;
    private final Long userId;
    private final Long postId;
    private final Integer likes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long id, String comment, Long userId, Long postId, Integer likes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
