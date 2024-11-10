package com.leets.team2.xclone.exception;

public class NotFoundAccessTokenException extends ApplicationException {


  public NotFoundAccessTokenException() {
    super(ErrorInfo.NOT_FOUND_ACCESS_TOKEN);
  }
}
