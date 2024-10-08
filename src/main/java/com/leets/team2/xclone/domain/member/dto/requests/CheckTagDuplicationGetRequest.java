package com.leets.team2.xclone.domain.member.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CheckTagDuplicationGetRequest(
    @NotNull(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 4, max = 12, message = "닉네임은 4자 이상 12자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "닉네임은 영문, 숫자, '_'만 사용 가능합니다.")
    String tag
) {

}
