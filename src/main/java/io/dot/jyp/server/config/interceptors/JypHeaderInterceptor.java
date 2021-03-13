package io.dot.jyp.server.config.interceptors;

import io.dot.jyp.server.config.properties.JypProperties;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class JypHeaderInterceptor implements ClientHttpRequestInterceptor {

  private final JypProperties jypProperties;

  public JypHeaderInterceptor(JypProperties jypProperties) {
    super();
    this.jypProperties = jypProperties;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders headers = request.getHeaders();
    //headers.add("Authorization", "Bearer " + this.jypProperties.getAccessToken());
    return execution.execute(request, body);
  }
}
