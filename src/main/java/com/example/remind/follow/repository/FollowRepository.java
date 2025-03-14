package com.example.remind.follow.repository;

import com.example.remind.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByToUserIdAndFromUserId(Long toUser, Long fromUser);

    // 로그인 한 사람이 팔로우 한 사람들 목록
    List<Follow> findAllByFromUserId(Long fromUserId);

    // 로그인 한 사람을 팔로우 한 사람들 목록
    List<Follow> findAllByToUserId(Long toUserId);

    // 내가 팔로잉 한 사람 중에 원하는 사람 추출
    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
