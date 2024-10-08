package com.leets.team2.xclone.domain.auth.dto.response;

public record OAuthLoginResponse(
    Boolean requiredRegister,
    String nickname
) {

}
