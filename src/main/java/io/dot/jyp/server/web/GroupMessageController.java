package io.dot.jyp.server.web;

import io.dot.jyp.server.domain.GroupMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class GroupMessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public GroupMessage sendMessage(@Payload GroupMessage groupMessage) {
        return groupMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public GroupMessage addUser(@Payload GroupMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}