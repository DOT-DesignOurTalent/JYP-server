package io.dot.jyp.server.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginRequest {

  private String email;
  private String passphrase;
}
