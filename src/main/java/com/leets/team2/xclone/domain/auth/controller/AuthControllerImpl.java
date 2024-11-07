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
    // 최초 로그인이 아니라면 바로 로그인 되도록 JWT 쿠키에 응답
    if (!oAuthLoginResponse.requiredRegister()) {
      JwtWrapper jwtWrapper = this.authService.generateJwt(kakaoInfo.id());
      cookies.add(this.cookieUtils.addCookie(jwtWrapper.accessToken(), CookieSettings.ACCESS_TOKEN,
          CookieMaxAge.HALF_HOUR));
      cookies.add(
          this.cookieUtils.addCookie(jwtWrapper.refreshToken(), CookieSettings.REFRESH_TOKEN,
              CookieMaxAge.ONE_DAY));
    }
    // 최초 로그인이라면 kakaoId를 쿠키에 담아 응답 -> 회원가입 시 필요하므로
    else {
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

    // 회원가입 완료시 JWT 발급하여 쿠키로 응답
    JwtWrapper jwtWrapper = this.authService.generateJwt(registerPostRequest.tag());
    List<ResponseCookie> cookies = new ArrayList<>();
    cookies.add(this.cookieUtils.addCookie(jwtWrapper.accessToken(), CookieSettings.ACCESS_TOKEN,
        CookieMaxAge.HALF_HOUR));
    cookies.add(this.cookieUtils.addCookie(jwtWrapper.refreshToken(), CookieSettings.REFRESH_TOKEN,
        CookieMaxAge.ONE_DAY));

    // 회원가입시 사용된 kakaoId 쿠키는 삭제
    cookies.add(this.cookieUtils.deleteCookie(CookieSettings.KAKAO_ID));

    return this.cookieUtils.addCookiesToResponse(responseData, cookies);
  }
}
