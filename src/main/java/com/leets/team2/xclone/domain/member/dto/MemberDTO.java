package com.leets.team2.xclone.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberDTO {
    @Builder
    public record Response(
            LocalDateTime birthDate,
            String tag,
            String nickname,
            String introduction,
            String profilePicture
    ){}
}