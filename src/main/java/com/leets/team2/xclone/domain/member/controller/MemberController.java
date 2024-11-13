package com.leets.team2.xclone.domain.member.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.dto.MemberDTO;
import com.leets.team2.xclone.domain.member.dto.requests.CheckTagDuplicationGetRequest;
import com.leets.team2.xclone.domain.member.dto.requests.MemberFindGetRequest;
import com.leets.team2.xclone.domain.member.dto.responses.CheckTagDuplicationGetResponse;
import com.leets.team2.xclone.domain.member.dto.responses.MemberFindGetResponse;
import com.leets.team2.xclone.domain.member.dto.responses.MemberTagGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface MemberController {

  @Operation(summary = "Tag 중복확인 API", description = "태그가 중복되는지 확인합니다.")
  @ApiResponse(responseCode = "200", description = "태그 중복확인 성공", content = @Content(schema = @Schema(implementation = CheckTagDuplicationGetResponse.class)))
  ResponseEntity<ApiData<CheckTagDuplicationGetResponse>> getCheckTagDuplication(
      CheckTagDuplicationGetRequest request);

  @Operation(summary = "멤버 정보 가져오기 API", description = "Tag로 멤버 정보를 찾습니다.")
  ResponseEntity<ApiData<MemberDTO.Response>> getMemberInfo(@RequestParam String tag);

  @Operation(summary = "프로필 사진 수정 API", description = "자신의 프로필 사진을 수정합니다.")
  ResponseEntity<ApiData<MemberDTO.Response>> updateProfilePicture(@RequestPart(value="image",required = false) MultipartFile image);

  @Operation(summary = "멤버 Tag 가져오기 API", description = "로그인한 멤버의 Tag를 가져옵니다.")
  ResponseEntity<ApiData<MemberTagGetResponse>> getMemberTag();
}
