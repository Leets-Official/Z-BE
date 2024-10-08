package com.leets.team2.xclone.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record OAuthLoginResponse(
    @Schema(description = "최초 로그인 여부, 최초 로그인이면 회원가입 페이지로 이동해야합니다.", example = "true")
    Boolean requiredRegister,
    @Schema(description = "멤버의 닉네임, 모든 응답에 반환됩니다.", example = "kimjooyoung")
    String nickname
) {

}
