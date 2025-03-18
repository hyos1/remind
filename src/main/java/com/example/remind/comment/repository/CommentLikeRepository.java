package com.example.remind.comment.repository;

import com.example.remind.comment.entity.Comment;
import com.example.remind.comment.entity.CommentLike;
import com.example.remind.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByUserAndComment(User user, Comment comment);
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    //댓글에 좋아요 누른 사람들 정보
    Integer countByComment(Comment comment);
}
