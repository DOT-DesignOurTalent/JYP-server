package io.dot.jyp.server.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceFileProperties {

  public static final String RESOURCE_FILE_PROPERTIES_PREFIX = "app.jyp.file";

  private String nicknamePath;
}
