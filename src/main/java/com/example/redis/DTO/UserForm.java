package com.example.redis.DTO;

import com.example.redis.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserForm {
    private String id;
    private String pw;
    private String nickname;
    private String name;
    private String email;

    public User toEntity() { return new User(id, pw, nickname, name, email); }
}