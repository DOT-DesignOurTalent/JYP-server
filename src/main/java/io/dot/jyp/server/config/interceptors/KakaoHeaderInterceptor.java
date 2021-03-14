package io.dot.jyp.server.config.interceptors;

import io.dot.jyp.server.config.properties.KakaoLocalProperties;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class KakaoHeaderInterceptor implements ClientHttpRequestInterceptor {

  private final KakaoLocalProperties kakaoLocalProperties;

  public KakaoHeaderInterceptor(KakaoLocalProperties kakaoLocalProperties) {
    super();
    this.kakaoLocalProperties = kakaoLocalProperties;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    HttpHeaders headers = request.getHeaders();
    headers.add("Authorization", "KakaoAK " + this.kakaoLocalProperties.getAuthorization());
    return execution.execute(request, body);
  }
}
