package com.leets.team2.xclone.utils.cookie;

import lombok.Getter;

@Getter
public enum CookieSettings {

  ACCESS_TOKEN("accessToken"),
  REFRESH_TOKEN ("refreshToken"),
  KAKAO_ID("kakaoId");

  private final String name;

  CookieSettings(String name) {
    this.name = name;
  }
}
