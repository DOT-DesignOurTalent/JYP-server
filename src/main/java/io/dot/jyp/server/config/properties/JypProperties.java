package io.dot.jyp.server.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JypProperties {

  public static final String JYP_PROPERTIES_PREFIX = "app.jyp";

  private String url;
  private String secret;
  private String accessToken;
}
