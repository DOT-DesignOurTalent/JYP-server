package io.dot.jyp.server.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.WebSocketSession;

public interface GroupClient {
  ObjectMapper objectMapper = new ObjectMapper();
  public <T> void sendMessage(WebSocketSession session, T message);
}
