package com.example.remind.post.controller;

import com.example.remind.auth.annotation.Auth;
import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.dto.request.PostRequestDto;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // 게시물 전체 조회, or 특정 사용자의 게시물 전체 조회(수정일 기준)
    @GetMapping
    public Page<PostResponseDto> findAllPosts(
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "userId", required = false) Long userId
                                              ) {
        System.out.println("게시글 전체조회 컨트롤러 확인");
        return postService.findAllPosts(pageNum, pageSize, userId);
    }

    // 게시물 좋아요 순 정렬
    @GetMapping("/likes")
    public Page<PostResponseDto> findAllPostsSortedByLikes(
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return postService.findAllPostsSortedByLikes(pageNum, pageSize);
    }

    // 게시물 기간 조회
    @GetMapping("/search-by-date")
    public List<PostResponseDto> findPostsByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return postService.findPostsByDate(startDate, endDate);
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

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(
            @Auth AuthUser authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        postService.deletePostById(authUser, postId);
    }
}
