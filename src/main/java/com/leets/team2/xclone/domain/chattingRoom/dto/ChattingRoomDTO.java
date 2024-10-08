package com.leets.team2.xclone.domain.chattingRoom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class ChattingRoomDTO {

    @Builder
    public record Save(
            @NotNull String chatMemberTag
    ){}

    @Builder
    public record Response(
            Long id,
            String participantNickname,
            String participantTag
    ){}
}
