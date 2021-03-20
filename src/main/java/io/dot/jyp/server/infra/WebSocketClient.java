package io.dot.jyp.server.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dot.jyp.server.domain.GroupClient;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketClient implements GroupClient {
  private final ObjectMapper objectMapper = new ObjectMapper();
  public <T> void sendMessage(WebSocketSession session, T message) {
    try {
      session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    } catch (IOException e) {
    }
  }
}
