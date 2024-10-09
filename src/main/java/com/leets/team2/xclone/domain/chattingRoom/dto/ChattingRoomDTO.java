package com.leets.team2.xclone.domain.chattingRoom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class ChattingRoomDTO {

    @Builder
    public record Save(
            @NotNull(message = "chatMemberTag는 필수 값입니다.")
            String chatMemberTag
    ){}

    @Builder
    public record Response(
            Long id,
            String participantNickname,
            String participantTag
    ){}
}
