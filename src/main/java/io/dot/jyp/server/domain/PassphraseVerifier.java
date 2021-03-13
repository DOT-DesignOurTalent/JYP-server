package io.dot.jyp.server.domain;

public interface PassphraseVerifier {

  void validate(String accountId, String passphrase);
}
