package com.leets.team2.xclone.common.oauth.kakao;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2.kakao")
@Getter
@Setter
public class KakaoOAuthConfigProperties {

  private String clientId;
  private String redirectUri;
  private List<String> scope;
  private String authorizationUri;
  private String accessTokenUri;
  private String userInfoEndpoint;
}
