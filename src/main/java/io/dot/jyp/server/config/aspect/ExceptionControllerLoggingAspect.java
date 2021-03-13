package io.dot.jyp.server.config.aspect;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class ExceptionControllerLoggingAspect {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionControllerLoggingAspect.class);

  private final List<String> ignoringEndpoints;

  public ExceptionControllerLoggingAspect(List<String> ignoringEndpoints) {
    this.ignoringEndpoints = ignoringEndpoints;
  }

  @Before("execution(* io.dot.jyp.server.web.error.DefaultExceptionHandler.*(..))")
  public void beforeGlobalExceptionHandler(JoinPoint joinPoint) throws IOException {
    String controllerName = joinPoint.getTarget().getClass().getSimpleName();
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();
    LOG.info(
        "[{}] method={}, uri={}, arguments={}, body={}",
        controllerName,
        request.getMethod(),
        request.getRequestURI(),
        this.getParams(request),
        this.isIgnoringEndpoint(request) ? "{}" : this.getBody(request)
    );
  }

  private JSONObject getParams(HttpServletRequest request) {
    JSONObject jsonObject = new JSONObject();
    Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
      String param = params.nextElement();
      String replaceParam = param.replaceAll("\\.", "-");
      try {
        jsonObject.put(replaceParam, request.getParameter(param));
      } catch (JSONException e) {
        throw new NullPointerException("null key");
      }
    }
    return jsonObject;
  }

  private JSONObject getBody(HttpServletRequest request) throws IOException {
    String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    try {
      return (body.equals("") || body == null) ? new JSONObject() : new JSONObject(body);
    } catch (JSONException e) {
      throw new NullPointerException("null key");
    }
  }

  private boolean isIgnoringEndpoint(HttpServletRequest request) {
    return this.ignoringEndpoints
        .stream()
        .anyMatch(paths -> paths.contains(request.getServletPath()));
  }
}
