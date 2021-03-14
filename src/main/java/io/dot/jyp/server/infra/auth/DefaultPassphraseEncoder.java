package io.dot.jyp.server.infra.auth;

import io.dot.jyp.server.domain.PassphraseEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPassphraseEncoder implements PassphraseEncoder {

  private final PasswordEncoder passwordEncoder;

  public DefaultPassphraseEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(CharSequence passphrase) {
    return this.passwordEncoder.encode(passphrase);
  }
}