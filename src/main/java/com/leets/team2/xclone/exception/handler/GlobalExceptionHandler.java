package com.leets.team2.xclone.exception.handler;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.exception.ApplicationException;
import com.leets.team2.xclone.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String INTERNAL_SERVER_ERROR = "서버에 오류가 발생했습니다.\n잠시후 다시 시도해주세요.";

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ApiData<String>> handleApplicationException(ApplicationException e) {
    return ApiData.errorFrom(e.getErrorInfo());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public String handleAnyRunTimeException(RuntimeException e) {
    log.warn("Unexpected Error Occurred", e);
    return INTERNAL_SERVER_ERROR;
  }



  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String handleAnyCheckedException(Exception e) {
    log.error("Unexpected Error Occurred", e);
    return INTERNAL_SERVER_ERROR;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiData<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return ApiData.validationFailure(e.getFieldErrors());
  }

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ApiData<String>> handlePostNotFoundException(PostNotFoundException e){
    return ApiData.errorFrom(e.getErrorInfo());
  }
}
