package com.example.redis.Config;

import com.example.redis.Security.JwtFilter;
import com.example.redis.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    //https://www.youtube.com/watch?v=YEB0Ln6Lcyk&ab_channel=KyeongrokKim 8:51
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                // 특정 API에 대해 모든 사용자에게 접근 허용
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/board/return").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/search/recommand").permitAll()
                .requestMatchers("/search/updateData").permitAll()
                // --------------------------------------------
                .anyRequest().authenticated() // 나머지 API에 대해서는 인증을 요구
                .and()
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}