package com.leets.team2.xclone.domain.member.service;

import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public boolean checkMemberExistsBy(String tag) {
    return this.memberRepository.existsByTag(tag);
  }
}
