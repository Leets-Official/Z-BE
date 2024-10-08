package com.leets.team2.xclone.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.oauth.kakao.KakaoURLBuilder;
import com.leets.team2.xclone.domain.auth.service.AuthService;
import com.leets.team2.xclone.domain.member.dto.responses.KakaoInfo;
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

  @Override
  @GetMapping("/kakao")
  public void getLogin(HttpServletResponse httpServletResponse)
      throws IOException {
     httpServletResponse.sendRedirect(this.kakaoURLBuilder.getAuthorizationUrl());
  }

  @GetMapping("/kakao/redirect")
  public ResponseEntity<ApiData<String>> getRedirect(
      @RequestParam String code
  ) throws JsonProcessingException {
    KakaoInfo kakaoInfo = this.authService.getMemberInfoFromKakao(code);

    return null;
  }
}
