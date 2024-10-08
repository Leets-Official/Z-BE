package com.leets.team2.xclone.utils.cookie;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.config.ConfigProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

  private final ConfigProperties configProperties;

  private final boolean cookieSecure;
  private final String cookieDomain;
  private final String cookiePath;
  private final String sameSite;

  public CookieUtils(ConfigProperties configProperties) {
    this.configProperties = configProperties;

    this.cookieSecure = configProperties.isCookieSecure();
    this.cookieDomain = configProperties.getCookieDomain();
    this.cookiePath = configProperties.getCookiePath();
    this.sameSite = configProperties.getCookieSameSite();
  }

  public <T> void addCookie(String value, CookieSettings cookieName,
      CookieMaxAge maxAge, ResponseEntity<ApiData<T>> response) {
    BodyBuilder bodyBuilder = ResponseEntity.status(response.getStatusCode());
    bodyBuilder.header(HttpHeaders.COOKIE,
        generate(cookieName.getName(), value, maxAge.getMaxAge()).toString());
  }

  public ResponseCookie deleteCookie(CookieSettings cookieName) {
    return generate(cookieName.getName(), null, 0);
  }

  private ResponseCookie generate(String cookieName, String value, long maxAge) {
    return ResponseCookie.from(cookieName, value)
        .httpOnly(true)
        .secure(this.cookieSecure)
        .domain(this.cookieDomain)
        .path(this.cookiePath)
        .sameSite(this.sameSite)
        .maxAge(maxAge)
        .build();
  }
}
