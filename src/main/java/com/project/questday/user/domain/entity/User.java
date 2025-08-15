package com.project.questday.user.domain.entity;

import com.project.questday.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET user_delete = true WHERE user_id = ?")
@SQLRestriction("user_delete = false")
public class User extends BaseEntity {

    @Id
    @Comment("PK 아이디")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Comment("이메일")
    @Column(nullable = false, unique = true, length = 255)
    private String userEmail;

    @Comment("닉네임")
    @Column(nullable = false, length = 50)
    private String userNickname;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String userPassword;

    @Comment("논리적 삭제")
    @Column(nullable = false)
    @Where(clause = "user_delete = false")
    private Boolean userDelete = false;

    @Builder
    public User(String userEmail, String userNickname, String userPassword) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }

    public void updateProfile(String userEmail, String userNickname) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }

    public void updatePassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
