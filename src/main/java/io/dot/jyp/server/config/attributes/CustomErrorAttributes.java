package io.dot.jyp.server.config.attributes;

import io.dot.jyp.server.domain.exception.ErrorCode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    Map<String, Object> customErrorAttributes = new LinkedHashMap<>();
    Map<String, Object> errorBody = this.getErrorBody(webRequest);
    customErrorAttributes.put("error", errorBody);
    return customErrorAttributes;
  }

  private Map<String, Object> getErrorBody(WebRequest webRequest) {
    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("code", this.getCode(webRequest));
    this.addErrorDetails(errorBody, webRequest);
    return errorBody;
  }

  private Integer getCode(WebRequest webRequest) {
    Integer status = getAttribute(webRequest, "javax.servlet.error.status_code");
    HttpStatus httpStatus = HttpStatus.valueOf(status);
    if (httpStatus.is4xxClientError()) {
      return ErrorCode.BAD_REQUEST.getValue();
    }
    return ErrorCode.INTERNAL_SERVER.getValue();
  }

  @SuppressWarnings("unchecked")
  private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
    return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
  }

  private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest) {
    Throwable error = getError(webRequest);
    if (error != null) {
      while (error instanceof ServletException && error.getCause() != null) {
        error = error.getCause();
      }
      addErrorMessage(errorAttributes, error);
    }
    Object message = getAttribute(webRequest, "javax.servlet.error.message");
    if ((!StringUtils.isEmpty(message) || errorAttributes.get("message") == null)
        && !(error instanceof BindingResult)) {
      errorAttributes
          .put("message", StringUtils.isEmpty(message) ? "no message available" : message);
    }
  }

  private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
    BindingResult result = extractBindingResult(error);
    if (result == null) {
      errorAttributes.put("message", error.getMessage());
      return;
    }
    if (result.hasErrors()) {
      errorAttributes.put("message", "validation failed for object='" + result.getObjectName()
          + "'. error count: " + result.getErrorCount());
    } else {
      errorAttributes.put("message", "no errors");
    }
  }

  private BindingResult extractBindingResult(Throwable error) {
    if (error instanceof BindingResult) {
      return (BindingResult) error;
    }
    if (error instanceof MethodArgumentNotValidException) {
      return ((MethodArgumentNotValidException) error).getBindingResult();
    }
    return null;
  }
}