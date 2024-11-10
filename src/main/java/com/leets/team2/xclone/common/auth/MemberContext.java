package com.leets.team2.xclone.common.auth;


import com.leets.team2.xclone.domain.member.entities.Member;

public class MemberContext {

  private static final ThreadLocal<Member> currentMember = new ThreadLocal<>();

  public static void setMember(Member member) {
    currentMember.set(member);
  }

  public static Member getMember() {
    return currentMember.get();
  }

  public static void clear() {
    currentMember.remove();
  }
}
