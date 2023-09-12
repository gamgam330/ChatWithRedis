package com.example.redis.Controller;

import com.example.redis.DTO.ChatMessage;
import com.example.redis.Service.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    @Value("${jwt.secret}")
    private String secretKey;

    private final RedisTemplate<String, Object> redisTemplate;
    private final Util util;
    private final ChannelTopic channelTopic;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */

    //이게 채팅방마다 존재하는 TOPIC값 구독하는 부분
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        //String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        String nickname = util.getNickname(token, secretKey);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setSender("[알림]");
            message.setMessage(nickname + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }//내가 생각하기에는 한명이 채팅걸면 그사람도 채팅방이 열려야함. 그니까 현재 로그인된 회원의 토큰값에서 닉네임 해독해서 토픽 구독하고, 다른한명은 어떻게 해야할까? 그사람 토큰값이 있는게 아닌데....
    //Token은 닉네임값으로 만들었기때문에 준형이랑 연결하면서 Token부분도 다 수정해야함.
    //DB에 저장된 개개인의 토큰값을 가쟈와 둘다 토픽을 구독해야할듯?
}