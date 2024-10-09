package com.leets.team2.xclone.domain.message.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

public class MessageDTO {

    @Builder
    public record Save(
            @NotNull(message = "chatRoomId는 필수 값입니다.")
            Long chatRoomId,
            @NotNull(message = "content는 필수 값입니다.")
            String content
    ){}

    @Builder
    public record Response(
            Long id,
            String nickname,
            String tag,
            String message,
            Boolean seen,
            LocalDateTime sentAt
    ){}
}
