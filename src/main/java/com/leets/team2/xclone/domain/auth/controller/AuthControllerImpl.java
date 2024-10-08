package com.leets.team2.xclone.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.oauth.kakao.KakaoURLBuilder;
import com.leets.team2.xclone.domain.auth.dto.response.OAuthLoginResponse;
import com.leets.team2.xclone.domain.auth.service.AuthService;
import com.leets.team2.xclone.domain.auth.dto.response.KakaoInfo;
import com.leets.team2.xclone.utils.cookie.CookieMaxAge;
import com.leets.team2.xclone.utils.cookie.CookieSettings;
import com.leets.team2.xclone.utils.cookie.CookieUtils;
import com.leets.team2.xclone.utils.jwt.JwtWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

  private final AuthService authService;
  private final KakaoURLBuilder kakaoURLBuilder;
  private final CookieUtils cookieUtils;

  @Override
  @GetMapping("/kakao")
  public void getLogin(HttpServletResponse httpServletResponse)
      throws IOException {
     httpServletResponse.sendRedirect(this.kakaoURLBuilder.getAuthorizationUrl());
  }

  @GetMapping("/kakao/redirect")
  public ResponseEntity<ApiData<OAuthLoginResponse>> getRedirect(
      @RequestParam String code
  ) throws JsonProcessingException {
    KakaoInfo kakaoInfo = this.authService.getMemberInfoFromKakao(code);

    OAuthLoginResponse oAuthLoginResponse = this.authService.oauthLogin(kakaoInfo);
    ResponseEntity<ApiData<OAuthLoginResponse>> responseData = ApiData.ok(oAuthLoginResponse);

    if (!oAuthLoginResponse.requiredRegister()) {
      JwtWrapper jwtWrapper = this.authService.generateJwt(kakaoInfo.properties().nickname(), kakaoInfo.id());
      this.cookieUtils.addCookie(jwtWrapper.accessToken(), CookieSettings.ACCESS_TOKEN,
          CookieMaxAge.HALF_HOUR, responseData);
      this.cookieUtils.addCookie(jwtWrapper.refreshToken(), CookieSettings.REFRESH_TOKEN,
          CookieMaxAge.ONE_DAY, responseData);
    }

    return responseData;
  }
}
