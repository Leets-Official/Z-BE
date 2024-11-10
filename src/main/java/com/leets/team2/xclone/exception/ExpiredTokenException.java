package com.leets.team2.xclone.exception;

public class ExpiredTokenException extends ApplicationException {

  public ExpiredTokenException() {
    super(ErrorInfo.EXPIRED_TOKEN);
  }
}
