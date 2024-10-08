package com.leets.team2.xclone.exception;

public class NoSuchMemberException extends ApplicationException{

  public NoSuchMemberException() {
    super(ErrorInfo.NO_SUCH_MEMBER);
  }
}
