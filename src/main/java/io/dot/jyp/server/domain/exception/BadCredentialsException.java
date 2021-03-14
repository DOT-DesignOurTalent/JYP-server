package io.dot.jyp.server.domain.exception;

public class BadCredentialsException extends RuntimeException {

  public BadCredentialsException(String message) {
    super(message);
  }

  public BadCredentialsException(String message, Throwable t) {
    super(message, t);
  }
}