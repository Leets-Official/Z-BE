package com.leets.team2.xclone.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.oauth.kakao.KakaoURLBuilder;
import com.leets.team2.xclone.domain.auth.dto.request.RegisterPostRequest;
import com.leets.team2.xclone.domain.auth.dto.response.KakaoInfo;
import com.leets.team2.xclone.domain.auth.dto.response.OAuthLoginResponse;
import com.leets.team2.xclone.domain.auth.dto.response.RegisterPostResponse;
import com.leets.team2.xclone.domain.auth.service.AuthService;
import com.leets.team2.xclone.utils.cookie.CookieMaxAge;
import com.leets.team2.xclone.utils.cookie.CookieSettings;
import com.leets.team2.xclone.utils.cookie.CookieUtils;
import com.leets.team2.xclone.utils.jwt.JwtWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
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

    List<ResponseCookie> cookies = new ArrayList<>();
    if (!oAuthLoginResponse.requiredRegister()) {
      JwtWrapper jwtWrapper = this.authService.generateJwt(kakaoInfo.properties().nickname(), kakaoInfo.id());
      cookies.add(this.cookieUtils.addCookie(jwtWrapper.accessToken(), CookieSettings.ACCESS_TOKEN,
          CookieMaxAge.HALF_HOUR));
      cookies.add(
          this.cookieUtils.addCookie(jwtWrapper.refreshToken(), CookieSettings.REFRESH_TOKEN,
              CookieMaxAge.ONE_DAY));
    } else {
      cookies.add(this.cookieUtils.addCookie(String.valueOf(kakaoInfo.id()), CookieSettings.KAKAO_ID,
              CookieMaxAge.HALF_HOUR));
    }

    return this.cookieUtils.addCookiesToResponse(responseData, cookies);
  }

  @Override
  @PostMapping("/register")
  public ResponseEntity<ApiData<RegisterPostResponse>> postRegister(
      @RequestBody @Valid RegisterPostRequest registerPostRequest,
      HttpServletRequest httpServletRequest
      ) {
    RegisterPostResponse registerPostResponse = this.authService.register(
        registerPostRequest.nickname(),
        registerPostRequest.tag(),
        registerPostRequest.birthDate(),
        Long.valueOf(this.cookieUtils.getCookieValue(CookieSettings.KAKAO_ID, httpServletRequest))
    );
    ResponseEntity<ApiData<RegisterPostResponse>> responseData = ApiData.created(registerPostResponse);

    JwtWrapper jwtWrapper = this.authService.generateJwt(registerPostRequest.tag());
    List<ResponseCookie> cookies = new ArrayList<>();
    cookies.add(this.cookieUtils.addCookie(jwtWrapper.accessToken(), CookieSettings.ACCESS_TOKEN,
        CookieMaxAge.HALF_HOUR));
    cookies.add(this.cookieUtils.addCookie(jwtWrapper.refreshToken(), CookieSettings.REFRESH_TOKEN,
        CookieMaxAge.ONE_DAY));

    cookies.add(this.cookieUtils.deleteCookie(CookieSettings.KAKAO_ID));

    return this.cookieUtils.addCookiesToResponse(responseData, cookies);
  }
}
