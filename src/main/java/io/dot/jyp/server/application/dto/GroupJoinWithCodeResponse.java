package io.dot.jyp.server.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupJoinWithCodeResponse {

  private String nickname;

  private GroupJoinWithCodeResponse(String nickname) {
    this.nickname = nickname;
  }

  public static GroupJoinWithCodeResponse of(String nickname) {
    return new GroupJoinWithCodeResponse(nickname);
  }
}
