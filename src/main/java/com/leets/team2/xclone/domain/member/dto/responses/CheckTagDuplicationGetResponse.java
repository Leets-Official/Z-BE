package com.leets.team2.xclone.domain.member.dto.responses;

public record CheckTagDuplicationGetResponse(
    Boolean isDuplicate
) {

  public static CheckTagDuplicationGetResponse of(Boolean isDuplicate) {
    return new CheckTagDuplicationGetResponse(isDuplicate);
  }
}
