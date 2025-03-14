package com.example.remind.follow.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.follow.entity.Follow;
import com.example.remind.follow.repository.FollowRepository;
import com.example.remind.post.dto.response.PostResponseDto;
import com.example.remind.post.service.PostService;
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
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostService postService;


    public void follow(Long fromUserId, Long toUserId) {

        // 자기 자신 follow 안됨
        if (fromUserId.equals(toUserId)) {
            throw new IllegalStateException("자기 자신을 팔로우 할 수 없습니다.");
        }

        // 중복 follow 안됨
        if (followRepository.existsByToUserIdAndFromUserId(toUserId, fromUserId)) {
            throw new IllegalStateException("이미 팔로우 한 친구입니다.");
        }

        User fromUser = userRepository.findById(fromUserId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );
        User toUser = userRepository.findById(toUserId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        Follow follow = new Follow(LocalDateTime.now(), LocalDateTime.now(), fromUser, toUser);
        followRepository.save(follow);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getUserPosts(int pageNum, int pageSize, Long friendId, AuthUser authUser) {
        // 해당 페이지 있는지부터 확인
        userRepository.findById(friendId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        // 내가 팔로우 하는 특정 유저!
        if (!followRepository.existsByToUserIdAndFromUserId(friendId, authUser.getId())) {
            throw new IllegalStateException("팔로우하지 않아서 게시물을 볼 수 없습니다.");
        }

        return postService.findAllPosts(pageNum, pageSize, friendId);
    }
}
