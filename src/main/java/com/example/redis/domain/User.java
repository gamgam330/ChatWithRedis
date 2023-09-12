package com.example.redis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
// Entity 메소드에서는 절대로 setter 메소드를 만들지 않는다!
// getter, setter를 무작정 생성하는 경우 클래스의 인스턴스 값들이 언제 변경되는지 명확하게 알 수 없다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userid_generator")
    @Column(name = "userid")
    // IDENTITY: 데이터베이스의 자동 증가 기능을 사용하여 기본 키 값을 생성하는 방식
    // SEQUENCE: 데이터베이스의 시퀀스를 사용하여 기본 키 값을 생성하는 방식
    private long userid;

    @Column(length = 45, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String pw;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String email;

    public void setUserId(long userid) {
        this.userid = userid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User (String id, String pw, String nickname, String name, String email) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userid +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}