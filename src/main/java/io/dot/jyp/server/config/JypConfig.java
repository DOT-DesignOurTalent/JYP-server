package io.dot.jyp.server.config;

import io.dot.jyp.server.config.properties.ResourceFileProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JypConfig {

  @Bean
  @ConfigurationProperties(ResourceFileProperties.RESOURCE_FILE_PROPERTIES_PREFIX)
  public ResourceFileProperties resourceFileProperties() {
    return new ResourceFileProperties();
  }

  @Bean
  @Qualifier("nicknamePath")
  public String nicknameFilePath(ResourceFileProperties resourceFileProperties) {
    return resourceFileProperties.getNicknamePath();
  }
}
