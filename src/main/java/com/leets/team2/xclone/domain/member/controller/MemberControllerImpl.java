package com.leets.team2.xclone.domain.member.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.dto.MemberDTO;
import com.leets.team2.xclone.domain.member.dto.requests.CheckTagDuplicationGetRequest;
import com.leets.team2.xclone.domain.member.dto.responses.CheckTagDuplicationGetResponse;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController{

  private final MemberService memberService;

  @Override
  @GetMapping("/tag/is-duplication")
  public ResponseEntity<ApiData<CheckTagDuplicationGetResponse>> getCheckTagDuplication(
      @ModelAttribute @Valid CheckTagDuplicationGetRequest request) {
    return ApiData.ok(
        CheckTagDuplicationGetResponse.of(this.memberService.checkMemberExistsBy(request.tag()))
    );
  }

  @Override
  @GetMapping("")
  public ResponseEntity<ApiData<MemberDTO.Response>> getMemberInfo(@RequestParam String tag) {
    Member targetMember = memberService.findMemberByTag(tag);
    MemberDTO.Response response = MemberDTO.Response.builder()
            .birthDate(targetMember.getBirthDate())
            .tag(targetMember.getTag())
            .nickname(targetMember.getNickname())
            .introduction(targetMember.getIntroduction())
            .build();

    return ApiData.ok(response);
  }

}
