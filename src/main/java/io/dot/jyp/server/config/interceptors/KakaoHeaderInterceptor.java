package io.dot.jyp.server.config.interceptors;

import io.dot.jyp.server.config.properties.KakaoLocalProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KakaoHeaderInterceptor implements ClientHttpRequestInterceptor {
    private final KakaoLocalProperties kakaoLocalProperties;

    public KakaoHeaderInterceptor(KakaoLocalProperties kakaoLocalProperties) {
        super();
        this.kakaoLocalProperties = kakaoLocalProperties;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        //headers.add("Authorization", "Bearer " + this.kakaolocalProperties.getAccessToken());
        return execution.execute(request, body);
    }
}
