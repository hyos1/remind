package com.example.remind.post.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.dto.request.PostRequestDto;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.post.entity.Post;
import com.example.remind.post.repository.PostRepository;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(AuthUser authUser, PostRequestDto dto) {

        System.out.println("게시글 생성");
        // 게시글에 유저 정보 저장 후 게시글 업로드
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new IllegalStateException("해당 유저 없음")
        );
        Post post = new Post(dto.getTitle(), dto.getContent(), user);
        postRepository.save(post);

        //좋아요 수까지 포함
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId());
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts() {
        System.out.println("게시물 조회");
        List<Post> posts = postRepository.findAll();
        System.out.println("게시물 List생성");
        List<PostResponseDto> dtos = new ArrayList<>();
        System.out.println("게시물 응답 List생성");
        for (Post post : posts) {
            dtos.add(new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId()));
        }
        System.out.println("dtos에 정보 담기");
        return dtos;
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId());
    }

    // 게시물 수정
    @Transactional
    public PostResponseDto updatePost(AuthUser authUser, Long postId, PostRequestDto dto) {

        // 게시글 있는지부터 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 존재하지 않습니다.")
        );

        // 본인 확인
        if (!post.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("자기 게시글만 수정할 수 있습니다.");
        }

        // 수정 후 저장
        post.update(dto.getTitle(), dto.getContent());
        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId());

    }

    @Transactional
    public void deletePostById(AuthUser authUser, Long postId) {

        // Post 가져오기 위해서
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 존재하지 않습니다.")
        );
//        if (!postRepository.existsById(postId)) {
//            throw new IllegalStateException("게시글이 존재하지 않습니다.");
//        }


        // 본인 확인
        if (!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("자기 게시글만 삭제할 수 있습니다.");
        }

        postRepository.deleteById(post.getId());
//        postRepository.delete(post);
    }
}
