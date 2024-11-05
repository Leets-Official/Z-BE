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
  ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 멤버입니다.", 10002),

  NO_SUCH_POST(HttpStatus.NOT_FOUND,"해당 게시물을 찾을 수 없습니다.",10003),//게시물을 못 찾았을 때 에러 정보

  INVALID_FILE(HttpStatus.BAD_REQUEST,"png 형식만 가능합니다.",10004),

  UNAUTHORIZED(HttpStatus.FORBIDDEN,"권한이 없습니다.",10005),//권한 인증 실패의 경우, 게시물 수정, 삭제 시 예외처리

  NO_SUCH_COMMENT(HttpStatus.NOT_FOUND,"해당 댓글이 없습니다.",10006),

  // jwt 영역
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다.", 10100);

  private final HttpStatus statusCode;
  private final String message;
  private final int code;
}
