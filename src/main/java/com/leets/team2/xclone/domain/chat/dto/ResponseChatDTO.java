package com.leets.team2.xclone.domain.chat.dto;

import java.time.LocalDateTime;

public record ResponseChatDTO(
        Long id,
        String nickname,
        String tag,
        String content,
        Boolean seen,
        LocalDateTime sentAt
){
}
