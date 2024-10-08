package com.leets.team2.xclone.domain.auth.dto.response;

public record OAuth2ProviderResponse(
    String access_token,
    String expires_in,
    String scope,
    String token_type
) {

}
