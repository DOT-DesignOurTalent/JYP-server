package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateResponse {

  private String code;
  private String nickname;

  public static GroupCreateResponse of(
      String code,
      String nickname
  ) {
    return new GroupCreateResponse(
        code,
        nickname
    );
  }
}
