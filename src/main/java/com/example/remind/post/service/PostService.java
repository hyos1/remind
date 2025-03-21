package com.example.remind.post.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.dto.request.PostRequestDto;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.post.entity.Post;
import com.example.remind.post.repository.PostLikeRepository;
import com.example.remind.post.repository.PostRepository;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(AuthUser authUser, PostRequestDto dto) {

        // 게시글에 유저 정보 저장 후 게시글 업로드
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                () -> new IllegalStateException("해당 유저 없음")
        );
        Post post = new Post(dto.getTitle(), dto.getContent(), user, LocalDateTime.now());
        postRepository.save(post);

        //좋아요 수까지 포함
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId(), post.getCreatedAt(), post.getUpdatedAt());
    }

    // 게시물 수정일 기준 전체 조회, or 특정 사용자의 게시물 전체 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllPosts(int pageNum, int pageSize, Long userId) {

        // 정렬 방식
        Pageable sortPage = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Post> posts;

        // 게시물 전체 조회
        if (userId == null) {
            System.out.println("게시물 전체 조회 실행");
            posts = postRepository.findAll(sortPage);

        } else {
            // 특정 유저의 게시물 전체 조회
            System.out.println("특정 유저 게시물 전체 조회 실행");
            posts = postRepository.findByUserId(userId, sortPage);
        }

        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikes(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시물 좋아요 많은 순으로 정렬
    public Page<PostResponseDto> findAllPostsSortedByLikes(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Post> posts = postRepository.findAllByOrderByLikesDesc(pageable);

        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikes(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시물 기간별 검색
    @Transactional(readOnly = true)
    public List<PostResponseDto> findPostsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Post> postsByDate = postRepository.findByCreatedAtBetween(startDate, endDate);

        List<PostResponseDto> dtos = new ArrayList<>();
        for (Post post : postsByDate) {
            dtos.add(new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLikes(),
                    post.getUser().getId(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            ));
        }
        return dtos;
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("게시글이 존재하지 않습니다.")
        );
        Integer likeCount = postLikeRepository.countByPost(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), likeCount, post.getUser().getId(), post.getCreatedAt(), post.getUpdatedAt());
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
        post.update(dto.getTitle(), dto.getContent(), LocalDateTime.now());
        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getLikes(), post.getUser().getId(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Transactional
    public void deletePostById(AuthUser authUser, Long postId) {

        // Post 가져오기 위해서
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글이 존재하지 않습니다.")
        );

        // 본인 확인
        if (!authUser.getId().equals(post.getUser().getId())) {
            throw new IllegalStateException("자기 게시글만 삭제할 수 있습니다.");
        }

        postRepository.deleteById(post.getId());
    }

}
