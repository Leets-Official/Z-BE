package com.leets.team2.xclone.utils.cookie;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CookieMaxAge {
  HALF_HOUR(1800000L),
  ONE_DAY(86400000L);

  private Long maxAge;
}
