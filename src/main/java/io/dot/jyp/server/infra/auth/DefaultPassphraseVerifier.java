package io.dot.jyp.server.infra.auth;

import io.dot.jyp.server.domain.AuthenticationService;
import io.dot.jyp.server.domain.PassphraseVerifier;
import io.dot.jyp.server.domain.exception.AuthenticationException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class DefaultPassphraseVerifier implements PassphraseVerifier {

  private final AuthenticationService authenticationService;

  public DefaultPassphraseVerifier(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public void validate(String accountId, String passphrase) {
    if (!this.authenticationService.authenticate(accountId, passphrase)) {
      throw new AuthenticationException("passphrase is invalid", ErrorCode.INVALID_PASSPHRASE);
    }
  }
}