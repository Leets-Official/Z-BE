package com.leets.team2.xclone.domain.follow.dto;

import jakarta.validation.constraints.NotNull;

public class FollowDTO {

    public record Save(
            @NotNull String tag
    ){}

    public record Response(
            Long id,
            String tag,
            String nickname
    ){}

}
