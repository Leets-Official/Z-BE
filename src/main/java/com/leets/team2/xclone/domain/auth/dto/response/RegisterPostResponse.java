package com.leets.team2.xclone.domain.auth.dto.response;

public record RegisterPostResponse(
    Boolean isRegistered
) {

  public static RegisterPostResponse success() {
    return new RegisterPostResponse(Boolean.TRUE);
  }
}
