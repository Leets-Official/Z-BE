package com.leets.team2.xclone.domain.chattingRoom.dto;

import jakarta.validation.constraints.NotBlank;

public class ResponseChattingRoomDTO {
    private Long id;

    @NotBlank
    private String participantNickname;

    @NotBlank
    private String participantTag;

}
