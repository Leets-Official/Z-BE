package com.leets.team2.xclone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorInfo {

  // validation 실패
  NOT_VALID_REQUEST(HttpStatus.BAD_REQUEST, "요청을 검증하는데 실패했습니다.", 9999),

  // Member 영역
  NO_SUCH_MEMBER(HttpStatus.NOT_FOUND, "해당 멤버를 찾을 수 없습니다.", 10001),

  // jwt 영역
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다.", 10100);

  private final HttpStatus statusCode;
  private final String message;
  private final int code;
}
