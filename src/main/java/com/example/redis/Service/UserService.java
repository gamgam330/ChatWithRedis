package com.example.redis.Service;

import com.example.redis.DTO.LoginForm;
import com.example.redis.DTO.UserForm;
import com.example.redis.Repository.UserRepository;
import com.example.redis.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String secretKey;

    public UserService(UserRepository userRepository, @Value("${jwt.secret}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    public String login(LoginForm loginForm) {

        // 1. Id가 틀린 경우
        User user = userRepository.findById(loginForm.getId())
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다"));

        // 2. Pw가 틀린 경우
        String userPasswordFromDB = user.getPw();
        if (!loginForm.getPw().equals(userPasswordFromDB)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인이 정상적으로 되었을 경우!
        //long userid = user.getUserid(); // id값을 기준으로 userid가져오기!

        String nickname = user.getNickname();

        return Util.createJwt(nickname, secretKey);
    }

    @Transactional
    public String join(UserForm form) {

        // 1. Id가 중복인지?
        userRepository.findById(form.getId())
                .ifPresent(user -> {
                    throw new RuntimeException("id: " + form.getId() + "은 중복입니다!");
                });

        // 2. e-mail이 중복인지?
        userRepository.findByEmail(form.getId())
                .ifPresent(user -> {
                    throw new RuntimeException("email: " + form.getEmail() + "은 중복입니다!");
                });

        // 3. 닉네임이 중복인지?
        userRepository.findNicknameById(form.getId())
                .ifPresent(user -> {
                    throw new RuntimeException("Nickname: " + form.getNickname() + "은 중복입니다!");
                });

        // 4. 그게 다 아니면 저장
        User user = form.toEntity();        // Dto를 Entity 변환
        userRepository.save(user); // Repository에게 Entity를 DB로 저장하게 함

        return "SUCCESS";
    }
}