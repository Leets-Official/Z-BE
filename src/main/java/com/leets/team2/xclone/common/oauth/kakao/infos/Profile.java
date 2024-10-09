package com.leets.team2.xclone.common.oauth.kakao.infos;

public record Profile(
    String nickname,
    String thumbnail_image_url,
    String profile_image_url,
    boolean is_default_image,
    boolean is_default_nickname
) {}