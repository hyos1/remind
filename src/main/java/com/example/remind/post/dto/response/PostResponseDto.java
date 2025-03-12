package com.example.remind.post.dto.response;

import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Integer likes;
    private final Long userId;

    public PostResponseDto(Long id, String title, String content, Integer likes, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.userId = userId;
    }
}
