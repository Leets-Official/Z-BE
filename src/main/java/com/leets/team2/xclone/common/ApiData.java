package com.leets.team2.xclone.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.leets.team2.xclone.exception.ErrorInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@JsonSerialize
@Builder
@Getter
public class ApiData<T> {

  private Boolean success;
  private T data;
  private Integer errorCode;
  private Object errorMessage;

  public static <T> ResponseEntity<ApiData<T>> successFrom(HttpStatus httpStatus, T data) {
    ApiData<T> apiData = ApiData.<T>builder()
        .success(true)
        .data(data)
        .build();
    return ResponseEntity.status(httpStatus).body(apiData);
  }

  public static <T> ResponseEntity<ApiData<T>> ok(T data) {
    ApiData<T> apiData = ApiData.<T>builder()
        .success(true)
        .data(data)
        .build();
    return ResponseEntity.ok(apiData);
  }

  public static <T> ResponseEntity<ApiData<T>> created(T data) {
    ApiData<T> apiData = ApiData.<T>builder()
        .success(true)
        .data(data)
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiData);
  }

  public static <T> ResponseEntity<ApiData<T>> validationFailure(List<FieldError> fieldErrors) {
    Map<String, String> errors = new HashMap<>();
    fieldErrors.forEach(
        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

    ApiData<T> apiData = ApiData.<T>builder()
        .success(false)
        .errorCode(ErrorInfo.NOT_VALID_REQUEST.getCode())
        .errorMessage(errors)
        .build();
    return ResponseEntity.ok(apiData);
  }

  public static ResponseEntity<ApiData<String>> errorFrom(ErrorInfo error) {
    ApiData<String> apiData = ApiData.<String>builder()
        .success(false)
        .errorCode(error.getCode())
        .errorMessage(error.getMessage())
        .build();
    return ResponseEntity.status(error.getStatusCode()).body(apiData);
  }
}
