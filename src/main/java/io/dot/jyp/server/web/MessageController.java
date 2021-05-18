package io.dot.jyp.server.web;

import io.dot.jyp.server.domain.GroupMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  private final SimpMessageSendingOperations messagingTemplate;

  public MessageController(SimpMessageSendingOperations messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("/group/message")
  public void message(GroupMessage message) {
    switch(message.getType()){
      case ENTER:
        message.setMessage(message.getSender() + "님이 입장하셨습니다.");
      case TALK:

      case EXIT:
        message.setMessage(message.getSender() + "님이 퇴장하셨습니다.");
      case READY:

      case START:
    }

    messagingTemplate.convertAndSend("/sub/group/" + message.getCode(), message);
  }

}