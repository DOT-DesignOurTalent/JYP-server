package io.dot.jyp.server.domain.exception;

public enum ErrorCode {
  BAD_REQUEST(4000),
  UNAUTHORIZED(4001),
  EMAIL_DOES_NOT_EXIST(4002),
  INVALID_PASSPHRASE(4003),
  DUPLICATED_EMAIL(4006),
  DUPLICATED_NICKNAME(4006),

  INTERNAL_SERVER(5000),
  INACTIVE_ACCOUNT(5002),
  FAIL_TO_SEND_EMAIL(5006);

  private final int value;
  private final String message;

  ErrorCode(int value, String message) {
    this.value = value;
    this.message = message;
  }

  ErrorCode(int value) {
    this.value = value;
    this.message = "";
  }

  public int getValue() {
    return this.value;
  }

  public String getMessage() {
    return message;
  }
}