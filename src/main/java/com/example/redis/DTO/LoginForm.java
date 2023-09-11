package com.example.redis.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginForm {
    private String id;
    private String pw;

    @Override
    public String toString() {
        return "UserForm {" +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }
}