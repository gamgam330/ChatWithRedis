package com.example.redis.DTO;

import com.example.redis.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserForm {

    private long userid;
    private String id;
    private String pw;
    private String nickname;
    private String name;
    private String email;

    @JsonCreator
    public UserForm (@JsonProperty("userid") long userid,
                     @JsonProperty("id") String id,
                     @JsonProperty("pw") String pw,
                     @JsonProperty("nickname") String nickname,
                     @JsonProperty("name") String name,
                     @JsonProperty("email") String email){
        this.userid = userid;
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserForm {" +
                "userId=" + userid +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User toEntity() { return new User(userid, id, pw, nickname, name, email); }
}