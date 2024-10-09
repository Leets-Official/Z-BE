package com.leets.team2.xclone.common.oauth.kakao.infos;

public record KakaoAccount(
    boolean profile_nickname_needs_agreement,
    boolean profile_image_needs_agreement,
    Profile profile
) {}
