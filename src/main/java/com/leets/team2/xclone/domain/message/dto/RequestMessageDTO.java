package com.leets.team2.xclone.domain.message.dto;

import jakarta.validation.constraints.NotNull;

public record RequestMessageDTO(
        @NotNull(message = "chatRoomId는 필수 값입니다.")
        Long chatRoomId,
        @NotNull(message = "content는 필수 값입니다.")
        String content) {

}
