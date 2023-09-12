package com.example.redis.Controller;

import com.example.redis.DTO.LoginForm;
import com.example.redis.DTO.UserForm;
import com.example.redis.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@CrossOrigin(origins = "http://121.165.182.97:3000", allowCredentials = "true")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor // 생성자 자동 생성
public class LoginController {

    private final UserService userService;

    // 추신: 시큐리티에서 패스워드 인코딩(암호화)도 지원해주는듯!
    @PostMapping("register")
    public ResponseEntity<String> Register (@RequestBody UserForm form) {

        userService.join(form);

        return ResponseEntity.ok().body("회원가입 성공!");
    }

    @PostMapping("login")
    public ResponseEntity<String> login (@RequestBody LoginForm loginForm) {

        System.out.println("loginForm = " + loginForm);

        String token = userService.login(loginForm);

        System.out.println(token);

        Cookie cookie = new Cookie("token", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(86400); // 1일
        cookie.setHttpOnly(false);

        System.out.println(cookie.getValue());

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return ResponseEntity.ok().body("로그인 성공!");
    }

    @PostMapping("loginTest")
    public  ResponseEntity<Long> loginTest (Authentication authentication) {
        // 토큰을 이용하여 userId 값을 가져옴
        String userId = authentication.getName();

        return ResponseEntity.ok().body(Long.parseLong(userId));
    }

}