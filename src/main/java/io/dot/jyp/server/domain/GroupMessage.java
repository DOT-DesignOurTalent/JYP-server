package io.dot.jyp.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessage {

  public enum MessageType {
    ENTER, TALK, EXIT, START, READY, ADD_MENUS
  }
  private MessageType type;
  private String code;
  private int senderPosition;
  private String message;
}