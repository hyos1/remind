package com.example.remind.user.entity;

import com.example.remind.follow.entity.Follow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phoneNum;
//    // 내가 팔로우 하는 사람
//    @OneToMany(mappedBy = "from_user", fetch = FetchType.LAZY)
//    private List<Follow> followings;
//    // 나를 팔로우 하는 사람
//    @OneToMany(mappedBy = "to_user", fetch = FetchType.LAZY)
//    private List<Follow> followers;


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> posts;

    public User(String name, String nickname, String email, String password, String phoneNum) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public void update(String name, String nickname, String email, String phoneNum) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phoneNum = phoneNum;
        System.out.println("변경 완료되었습니다!");
    }

    public void updatePassword(String password) {
        this.password = password;
        System.out.println("비밀번호가 변경되었습니다!");
    }
}
