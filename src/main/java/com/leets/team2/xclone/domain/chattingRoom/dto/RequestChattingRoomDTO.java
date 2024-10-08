package com.leets.team2.xclone.domain.chattingRoom.dto;

import jakarta.validation.constraints.NotBlank;

public class RequestChattingRoomDTO {
    @NotBlank
    private String chatMemberTag;

}
