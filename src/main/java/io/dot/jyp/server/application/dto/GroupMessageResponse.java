package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessageResponse {

  private int id;
  private String message;
  private String name;

  public static GroupMessageResponse of(
      int id,
      String message,
      String name
  ) {
    return new GroupMessageResponse(
        id,
        message,
        name
    );
  }
}
