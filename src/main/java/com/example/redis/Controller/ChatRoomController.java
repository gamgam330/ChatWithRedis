package com.example.redis.Controller;

import com.example.redis.DTO.ChatRoom;
import com.example.redis.Repository.ChatRoomRepository;
import com.example.redis.Service.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// import 생략...

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @Value("${jwt.secret}")
    private String secretKey;
    private final ChatRoomRepository chatRoomRepository;


    private final Util util;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}