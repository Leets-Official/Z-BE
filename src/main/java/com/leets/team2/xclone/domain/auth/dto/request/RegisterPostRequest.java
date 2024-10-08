package com.leets.team2.xclone.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterPostRequest(
    @NotNull(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 1, max = 8, message = "닉네임은 1자 이상 8자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "닉네임은 한글, 영문, 숫자만 사용 가능합니다.")
    String nickname,

    @NotNull(message = "태그는 필수 입력 항목입니다.")
    @Size(min = 4, max = 12, message = "태그는 4자 이상 12자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "닉네임은 영문, 숫자, '_'만 사용 가능합니다.")
    String tag,

    @NotNull(message = "생일은 필수 입력사항입니다.")
    LocalDate birthDate
) {

}
