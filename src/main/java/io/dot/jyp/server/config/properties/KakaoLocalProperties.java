package io.dot.jyp.server.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoLocalProperties {
    public static final String KAKAO_LOCAL_PROPERTIES_PREFIX = "app.social.kakao";

    private String clientId;
    private String appName;
    private String url;
    private String authorization;

}
