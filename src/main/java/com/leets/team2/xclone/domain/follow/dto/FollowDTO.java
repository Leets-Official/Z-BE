package com.leets.team2.xclone.domain.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class FollowDTO {

    @Builder
    public record Save(
            @NotNull String tag
    ){}

    @Builder
    public record Response(
            Long id,
            String tag,
            String nickname
    ){}

}
