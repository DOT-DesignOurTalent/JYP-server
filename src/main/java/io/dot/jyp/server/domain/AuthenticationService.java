package io.dot.jyp.server.domain;

public interface AuthenticationService {

  boolean authenticate(String id, String passphrase);
}