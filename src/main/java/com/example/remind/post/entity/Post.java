package com.example.remind.post.entity;

import com.example.remind.common.entity.BaseEntity;
import com.example.remind.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Integer likes = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addLike() {
        this.likes++;
    }

    public void removeLike() {
        this.likes--;
    }

    public Post(String title, String content, User user, LocalDateTime now) {
        super(now, now);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    //    public Post(String title, String content, User user) {
//        this.title = title;
//        this.content = content;
//        this.user = user;
//
//    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
