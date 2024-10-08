package com.leets.team2.xclone.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.auth.dto.response.OAuthLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Auth 관련 API")
public interface AuthController {

  @Operation(summary = "카카오 OAuth2 로그인 시작 엔드포인트", description = "카카오 로그인 시작 시 이 엔드포인트로 GET 요청을 보내서 카카오 로그인 페이지로 Redirect합니다.")
  void getLogin(HttpServletResponse httpServletResponse) throws IOException;

  @Operation(summary = "카카오 OAuth2 Redirect 엔드포인트", description = "카카오에서 받은 인가 코드를 전송하여 로그인을 진행합니다.")
  @ApiResponse(responseCode = "200", description = "카카오 OAuth2 로그인 성공", content = @Content(schema = @Schema(implementation = OAuthLoginResponse.class)))
  ResponseEntity<ApiData<OAuthLoginResponse>> getRedirect(String code) throws JsonProcessingException;
}
