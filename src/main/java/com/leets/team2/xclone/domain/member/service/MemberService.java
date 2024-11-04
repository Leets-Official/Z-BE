package com.leets.team2.xclone.domain.member.service;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private static final Log log = LogFactory.getLog(MemberService.class);
  private final MemberRepository memberRepository;

  public boolean checkMemberExistsBy(String nickname, Long kakaoId) {
    return this.memberRepository.existsByNicknameAndKakaoId(nickname, kakaoId);
  }

  public Member findMemberBy(String nickname, Long kakaoId) {
    return this.memberRepository.findByNicknameAndKakaoId(nickname, kakaoId).orElseThrow(
        NoSuchMemberException::new);
  }

  public void saveMember(Member member) {
    this.memberRepository.save(member);
  }

  public boolean checkMemberExistsBy(String tag) {
    return this.memberRepository.existsByTag(tag);
  }

  public boolean checkMemberExistByKakaoNicknameAndKakaoId(String nickname, Long kakaoId) {
    return this.memberRepository.existsByKakaoNicknameAndKakaoId(nickname, kakaoId);
  }

  public Member findNicknameByKakaoNicknameAndKakaoId(String kakaoNickname, Long kakaoId) {
    return this.memberRepository.findByKakaoNicknameAndKakaoId(kakaoNickname, kakaoId).orElseThrow(
        NoSuchMemberException::new);
  }
}
