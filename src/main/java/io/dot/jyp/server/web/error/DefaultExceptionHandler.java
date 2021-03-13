package io.dot.jyp.server.web.error;

import io.dot.jyp.server.domain.exception.AuthenticationException;
import io.dot.jyp.server.domain.exception.BadRequestException;
import io.dot.jyp.server.domain.exception.ErrorCode;
import io.dot.jyp.server.domain.exception.InternalServerException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ConditionalOnMissingBean(DefaultExceptionHandler.class)
public class DefaultExceptionHandler {

  @ExceptionHandler(value = {
      BadRequestException.class,
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleBadRequestIncludedErrorCodeException(BadRequestException e) {
    return createErrorResponse(e.getErrorCode(), e.getMessage());
  }

  @ExceptionHandler(value = {
      AuthenticationException.class,
  })
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected ErrorResponse handleAuthenticationException(AuthenticationException e) {
    return createErrorResponse(e.getErrorCode(), e.getMessage());
  }

  @ExceptionHandler(value = {
      InternalServerException.class,
  })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ErrorResponse handleInternalServerException(InternalServerException e) {
    return createErrorResponse(e.getErrorCode(), e.getMessage());
  }

  protected ErrorResponse createErrorResponse(ErrorCode errorCode, String responseMessage) {
    return new ErrorResponse(new ErrorBody(responseMessage, errorCode.getValue()));
  }
}
