package com.example.remind.comment.entity;

import com.example.remind.common.entity.BaseEntity;
import com.example.remind.post.entity.Post;
import com.example.remind.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(LocalDateTime createdAt, LocalDateTime updatedAt, String comment, User user, Post post) {
        super(createdAt, updatedAt);
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public void updateCommentLikes(Integer likes) {
        this.likes = likes;
    }
}
