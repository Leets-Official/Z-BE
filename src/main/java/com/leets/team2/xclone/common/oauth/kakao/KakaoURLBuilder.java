package com.leets.team2.xclone.common.oauth.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoURLBuilder{

  private final KakaoOAuthConfigProperties kakaoOAuthConfigProperties;

  public String getAuthorizationUrl() {
    StringBuilder uriBuilder = new StringBuilder();
    uriBuilder.append(this.kakaoOAuthConfigProperties.getAuthorizationUri())
        .append("?")
        .append("client_id=").append(this.kakaoOAuthConfigProperties.getClientId())
        .append("&redirect_uri=").append(this.kakaoOAuthConfigProperties.getRedirectUri())
        .append("&response_type=code");

    return uriBuilder.toString();
  }

  public String getTokenUrl(String code) {
    return this.kakaoOAuthConfigProperties.getAccessTokenUri();
  }

  public String getUserInfoUrl(String accessToken) {
    return this.kakaoOAuthConfigProperties.getUserInfoEndpoint();
  }
}
