package com.leets.team2.xclone.domain.follow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class RequestFollowDTO {
    @NotBlank
    private Long followingTarget;
    
}
