package com.leets.team2.xclone.utils.cookie;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.config.ConfigProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
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

  public ResponseCookie addCookie(String value, CookieSettings cookieName, CookieMaxAge maxAge) {
     return generate(cookieName.getName(), value, maxAge.getMaxAge());
  }

  public ResponseCookie deleteCookie(CookieSettings cookieName) {
    return generate(cookieName.getName(), null, 0);
  }

  public String getCookieValue(CookieSettings cookieName, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(cookieName.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }

  public <T> ResponseEntity<ApiData<T>> addCookiesToResponse(ResponseEntity<ApiData<T>> response, List<ResponseCookie> cookies) {
    // ResponseEntity 헤더에 Set-Cookie 헤더 추가
    BodyBuilder bodyBuilder = ResponseEntity.status(response.getStatusCode());
    cookies.forEach(cookie -> bodyBuilder.header(HttpHeaders.SET_COOKIE, cookie.toString()));
    return bodyBuilder.body(response.getBody());
  }

  private ResponseCookie generate(String cookieName, String value, long maxAge) {
    return ResponseCookie.from(cookieName, value)
        .httpOnly(true)
        .secure(this.cookieSecure)
        .path(this.cookiePath)
        .sameSite(this.sameSite)
        .domain(this.cookieDomain)
        .maxAge(maxAge)
        .build();
  }
}
