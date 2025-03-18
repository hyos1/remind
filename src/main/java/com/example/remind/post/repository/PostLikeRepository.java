package com.example.remind.post.repository;

import com.example.remind.post.entity.Post;
import com.example.remind.post.entity.PostLike;
import com.example.remind.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserAndPost(User user, Post post);
    Optional<PostLike> findByUserAndPost(User user, Post post);

    Integer countByPost(Post post);
}
