package com.leets.team2.xclone.domain.member.controller;

import com.leets.team2.xclone.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController{

  private final MemberService memberService;
}
