package com.example.remind.post.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.post.entity.Post;
import com.example.remind.post.entity.PostLike;
import com.example.remind.post.repository.PostLikeRepository;
import com.example.remind.post.repository.PostRepository;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // 특정 게시물과, 좋아요 누른 사람 저장
    @Transactional
    public void postLike(AuthUser authUser, Long postId) {

        // 방문한 게시물
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시물입니다.")
        );

        // 로그인 한 사람 정보
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("뭐라고 써야하지")
        );

        // 글 작성자와 좋아요 누른 사람 일치 확인
        if (post.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("자기 게시물에 좋아요를 남길 수 없습니다.");
        }

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("좋아요는 한 번만 가능합니다.");
        }

        PostLike postLike = new PostLike(user, post);
        postLikeRepository.save(postLike);

        // 게시물에 좋아요 수 저장
        Integer likeCount = postLikeRepository.countByPost(post);
        post.updatePostLikes(likeCount);
        postRepository.save(post);

    }

    // 게시물 좋아요 취소
    @Transactional
    public void deletePostLike(AuthUser authUser, Long postId) {

        // 방문한 게시물
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시물입니다.")
        );

        // 로그인 한 사람 정보
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("뭐라고 써야하지")
        );

        PostLike postLike = postLikeRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new IllegalStateException("좋아요 누른 적 없어서 취소 못함")
        );

        postLikeRepository.delete(postLike);

        // 게시물에 좋아요 취소 후 반영~
        Integer likeCount = postLikeRepository.countByPost(post);
        post.updatePostLikes(likeCount);
        postRepository.save(post);
    }
}
