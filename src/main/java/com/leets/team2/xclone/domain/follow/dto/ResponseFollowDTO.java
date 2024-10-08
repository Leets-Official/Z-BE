package com.leets.team2.xclone.domain.follow.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ResponseFollowDTO {
    private Long id;
    private String tag;
    private String nickname;

}
