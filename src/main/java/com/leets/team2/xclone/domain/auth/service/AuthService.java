package com.leets.team2.xclone.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.oauth.kakao.KakaoProviderClient;
import com.leets.team2.xclone.domain.auth.dto.response.KakaoInfo;
import com.leets.team2.xclone.domain.auth.dto.response.OAuthLoginResponse;
import com.leets.team2.xclone.domain.auth.dto.response.RegisterPostResponse;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.exception.AlreadyExistMemberException;
import com.leets.team2.xclone.utils.jwt.JwtUtils;
import com.leets.team2.xclone.utils.jwt.JwtWrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberService memberService;
  private final KakaoProviderClient kakaoProviderClient;
  private final JwtUtils jwtUtils;

  public KakaoInfo getMemberInfoFromKakao(String code) throws JsonProcessingException {
    return this.kakaoProviderClient.getMemberInfo(this.kakaoProviderClient.getAccessToken(code));
  }

  public OAuthLoginResponse oauthLogin(KakaoInfo kakaoInfo) {
    String nickname = kakaoInfo.properties().nickname();
    Long kakaoId = kakaoInfo.id();
    return new OAuthLoginResponse(
        !this.memberService.checkMemberExistsBy(nickname, kakaoId),
        nickname
    );
  }

  public JwtWrapper generateJwt(String nickname, Long kakaoId) {
    Member member = this.memberService.findMemberBy(nickname, kakaoId);
    return new JwtWrapper(
        this.jwtUtils.generateAccessToken(member.getTag()),
        this.jwtUtils.generateRefreshToken(member.getTag())
    );
  }

  public JwtWrapper generateJwt(String tag) {
    return new JwtWrapper(
        this.jwtUtils.generateAccessToken(tag),
        this.jwtUtils.generateRefreshToken(tag)
    );
  }

  public RegisterPostResponse register(String nickname, String tag, LocalDate birthDate, Long kakaoId) {
    if (this.memberService.checkMemberExistsBy(tag)) {
      throw new AlreadyExistMemberException();
    }

    Member newMember = Member.builder()
        .nickname(nickname)
        .tag(tag)
        .birthDate(LocalDateTime.of(birthDate, LocalTime.of(0, 0)))
        .kakaoId(kakaoId)
        .build();
    this.memberService.saveMember(newMember);

    return RegisterPostResponse.success();
  }
}
