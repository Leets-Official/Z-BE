package com.leets.team2.xclone.domain.member.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.dto.requests.CheckTagDuplicationGetRequest;
import com.leets.team2.xclone.domain.member.dto.responses.CheckTagDuplicationGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface MemberController {

  @Operation(summary = "Tag 중복확인 API", description = "태그가 중복되는지 확인합니다.")
  @ApiResponse(responseCode = "200", description = "태그 중복확인 성공", content = @Content(schema = @Schema(implementation = CheckTagDuplicationGetResponse.class)))
  ResponseEntity<ApiData<CheckTagDuplicationGetResponse>> getCheckTagDuplication(
      CheckTagDuplicationGetRequest request);
}
