package com.leets.team2.xclone.domain.member.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberFindGetRequest(
    @NotNull(message = "tag는 필수입니다.")
    @NotEmpty(message = "tag는 필수입니다.")
    String tag
) {

}
