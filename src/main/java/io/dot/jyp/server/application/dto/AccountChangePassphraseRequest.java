package io.dot.jyp.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountChangePassphraseRequest {

  @Schema(required = true)
  @ToString.Exclude
  private String passphrase;
  @Schema(required = true)
  private String newPassphrase;
}