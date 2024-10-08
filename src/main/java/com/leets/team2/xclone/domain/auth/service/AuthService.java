package com.leets.team2.xclone.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.oauth.kakao.KakaoProviderClient;
import com.leets.team2.xclone.domain.auth.dto.response.KakaoInfo;
import com.leets.team2.xclone.domain.auth.dto.response.OAuthLoginResponse;
import com.leets.team2.xclone.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberService memberService;
  private final KakaoProviderClient kakaoProviderClient;

  public KakaoInfo getMemberInfoFromKakao(String code) throws JsonProcessingException {
    return this.kakaoProviderClient.getMemberInfo(this.kakaoProviderClient.getAccessToken(code));
  }

  public OAuthLoginResponse oauthLogin(KakaoInfo kakaoInfo) {
    String nickname = kakaoInfo.properties().nickname();
    Long kakaoId = kakaoInfo.id();
    return new OAuthLoginResponse(
        this.memberService.checkMemberExistsBy(nickname, kakaoId),
        nickname
    );
  }
}
