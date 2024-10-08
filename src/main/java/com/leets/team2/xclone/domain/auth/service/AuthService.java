package com.leets.team2.xclone.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.oauth.kakao.KakaoOAuthConfigProperties;
import com.leets.team2.xclone.common.oauth.kakao.KakaoProviderClient;
import com.leets.team2.xclone.common.oauth.kakao.KakaoURLBuilder;
import com.leets.team2.xclone.domain.member.dto.responses.KakaoInfo;
import com.leets.team2.xclone.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberService memberService;
  private final KakaoProviderClient kakaoProviderClient;

  public KakaoInfo getMemberInfoFromKakao(String code) throws JsonProcessingException {
    return this.kakaoProviderClient.getMemberInfo(this.kakaoProviderClient.getAccessToken(code));
  }
}
