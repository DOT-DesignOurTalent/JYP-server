package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageRequest {

  private int id;
  private int messageId;
}
