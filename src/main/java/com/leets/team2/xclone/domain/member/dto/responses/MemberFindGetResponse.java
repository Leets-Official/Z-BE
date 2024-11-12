package com.leets.team2.xclone.domain.member.dto.responses;

import com.leets.team2.xclone.domain.member.entities.Member;
import java.util.ArrayList;
import java.util.List;

public record MemberFindGetResponse(
    List<MemberInfo> memberInfo
) {

  public static MemberFindGetResponse empty() {
    return new MemberFindGetResponse(new ArrayList<>());
  }

  record MemberInfo(
      String tag,
      String birthDate,
      String nickname,
      String introduction
  ) {
    public static MemberInfo of(Member member) {
      return new MemberInfo(
          member.getTag(),
          member.getBirthDate().toString(),
          member.getNickname(),
          member.getIntroduction()
      );
    }
  }

  public void add(Member member) {
    this.memberInfo.add(MemberInfo.of(member));
  }
}
