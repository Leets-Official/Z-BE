package com.leets.team2.xclone.exception;

public class NotFoundRefreshTokenException extends ApplicationException {

  public NotFoundRefreshTokenException() {
    super(ErrorInfo.NOT_FOUND_REFRESH_TOKEN);
  }
}
