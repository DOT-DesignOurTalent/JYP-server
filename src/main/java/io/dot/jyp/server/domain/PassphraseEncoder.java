package io.dot.jyp.server.domain;

public interface PassphraseEncoder {

  String encode(CharSequence passphrase);
}
