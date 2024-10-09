package com.leets.team2.xclone.domain.message.dto;

import java.time.LocalDateTime;

public record ResponseMessageDTO (
        Long id,
        String nickname,
        String tag,
        String message,
        Boolean seen,
        LocalDateTime sentAt
){
}
