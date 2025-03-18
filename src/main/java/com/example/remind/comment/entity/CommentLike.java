package com.example.remind.comment.entity;

import com.example.remind.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "commentlikes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"}))
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
