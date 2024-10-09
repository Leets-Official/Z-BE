package com.leets.team2.xclone.exception;

public class AlreadyExistMemberException extends ApplicationException{


  public AlreadyExistMemberException() {
    super(ErrorInfo.ALREADY_EXIST_MEMBER);
  }
}
