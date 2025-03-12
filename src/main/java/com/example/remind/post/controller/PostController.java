package com.example.remind.post.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.dto.request.PostRequestDto;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public PostResponseDto createPost(@Auth AuthUser authUser, @RequestBody PostRequestDto dto) {
        return postService.createPost(authUser, dto);
    }

    // 게시물 전체 조회
    @GetMapping
    public List<PostResponseDto> findAllPosts() {
        System.out.println("전체조회");
        return postService.findAllPosts();
    }

    // 게시물 단건 조회
    @GetMapping("/{postId}")
    public PostResponseDto findPostById(@PathVariable(name = "postId") Long postId) {
        return postService.findPostById(postId);
    }

    // 게시물 수정
    @PatchMapping("{postId}")
    public PostResponseDto updatePost(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId,
            @RequestBody PostRequestDto dto
    ) {
        return postService.updatePost(authUser, postId, dto);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.deletePostById(authUser, postId);
    }
}
