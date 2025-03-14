package com.example.remind.follow.entity;

import com.example.remind.common.entity.BaseEntity;
import com.example.remind.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 내가 팔로우 한 사람들(팔로잉)
    // 친구신청 요청한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

//    toUser가 "나"일 때는 팔로우

    // 나를 팔로우 한 사람들(팔로워)
    // 친구신청 받은 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    public Follow(LocalDateTime createdAt, LocalDateTime updatedAt, User fromUser, User toUser) {
        super(createdAt, updatedAt);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
