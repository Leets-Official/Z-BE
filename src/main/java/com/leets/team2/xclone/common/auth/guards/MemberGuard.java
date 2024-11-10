package com.leets.team2.xclone.common.auth.guards;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberGuard implements Guard {

  @Override
  public void canActivate() throws Exception {
    // accessToken 이 제대로 검증되어 파싱되었으면 그냥 통과
  }
}
