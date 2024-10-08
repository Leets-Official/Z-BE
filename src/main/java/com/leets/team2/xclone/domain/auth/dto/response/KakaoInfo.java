package com.leets.team2.xclone.domain.auth.dto.response;

import com.leets.team2.xclone.common.oauth.kakao.infos.KakaoAccount;
import com.leets.team2.xclone.common.oauth.kakao.infos.Properties;

public record KakaoInfo(
    long id,
    String connected_at,
    Properties properties,
    KakaoAccount kakao_account
) {}
