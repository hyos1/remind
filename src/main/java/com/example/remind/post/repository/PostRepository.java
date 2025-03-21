package com.example.remind.post.repository;

import com.example.remind.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
//    Page<Post> findUserPosts(Long userId, Pageable pageable);
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);
    // 좋아요 순으로 게시물 정렬
    Page<Post> findAllByOrderByLikesDesc(Pageable pageable);

    //날짜별 조회
    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
