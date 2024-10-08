package com.leets.team2.xclone.exception;

public class InvalidTokenException extends ApplicationException{

  public InvalidTokenException() {
    super(ErrorInfo.INVALID_TOKEN);
  }
}
