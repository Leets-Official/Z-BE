package com.leets.team2.xclone.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

  protected ErrorInfo errorInfo;

  protected ApplicationException(ErrorInfo errorInfo) {
    super(errorInfo.getMessage());
    this.errorInfo = errorInfo;
  }
}
