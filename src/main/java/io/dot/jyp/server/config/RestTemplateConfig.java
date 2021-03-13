package io.dot.jyp.server.config;

import io.dot.jyp.server.config.interceptors.JypHeaderInterceptor;
import io.dot.jyp.server.config.interceptors.KakaoHeaderInterceptor;
import io.dot.jyp.server.config.properties.JypProperties;
import io.dot.jyp.server.config.properties.KakaoLocalProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

  private static final int READ_TIMEOUT = 10000;
  private static final int CONNECT_TIMEOUT = 5000;
  private static final int MAX_CONN_TOTAL = 100;
  private static final int MAX_CONN_PER_ROUTE = 5;


  @Bean
  @ConfigurationProperties(JypProperties.JYP_PROPERTIES_PREFIX)
  public JypProperties jypProperties() {
    return new JypProperties();
  }

  @Bean
  @ConfigurationProperties(KakaoLocalProperties.KAKAO_LOCAL_PROPERTIES_PREFIX)
  public KakaoLocalProperties kakaoLocalProperties() {
    return new KakaoLocalProperties();
  }

  @Bean
  @Qualifier("jypApiRestTemplate")
  public RestTemplate jypApiRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(this.clientHttpRequestFactory());
    restTemplate.setUriTemplateHandler(
        new DefaultUriBuilderFactory(String.format("%s/api/v1", this.jypProperties().getUrl())));
    restTemplate
        .setInterceptors(Collections.singletonList(new JypHeaderInterceptor(this.jypProperties())));
    return restTemplate;
  }

  @Bean
  @Qualifier("kakaoLocalApiRestTemplate")
  public RestTemplate kakaoLocalApiRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(this.clientHttpRequestFactory());
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(
        String.format("%s/v2/local", this.kakaoLocalProperties().getUrl())));
    restTemplate.setInterceptors(
        Collections.singletonList(new KakaoHeaderInterceptor(this.kakaoLocalProperties())));
    return restTemplate;
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setReadTimeout(READ_TIMEOUT);
    factory.setConnectTimeout(CONNECT_TIMEOUT);

    HttpClient httpClient = HttpClientBuilder.create()
        .setMaxConnTotal(MAX_CONN_TOTAL)
        .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
        .build();
    factory.setHttpClient(httpClient);
    return factory;
  }

  private List<HttpMessageConverter<?>> getHttpMessageConverters() {
    List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    httpMessageConverters.add(converter);
    return httpMessageConverters;
  }
}
