package com.leets.team2.xclone.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ConfigProperties {

  private String baseUrl;
  private String registerRedirectUrl;

  private boolean cookieSecure;
  private String cookieDomain;
  private String cookiePath;
  private String cookieSameSite;
  private String accessTokenMaxAge;
  private String refreshTokenMaxAge;
}
