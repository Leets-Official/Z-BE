package com.leets.team2.xclone.domain.member.entities;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.image.converter.ImageListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`member`")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member extends AbstractEntity {

  @Column(name = "birth_date", nullable = true)
  private LocalDateTime birthDate;

  @Column(name = "tag", nullable = false, unique = true)
  private String tag;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "introduction", nullable = true)
  private String introduction;

  @Column(name = "kakao_id", nullable = false, unique = true)
  private Long kakaoId;

  @Column(name="image_url")
  @Convert(converter = ImageListConverter.class)
  private List<String> imageUrl = new ArrayList<>();

  public void setImage(String profileImageUrl) {
    if (this.imageUrl == null) {
      this.imageUrl = new ArrayList<>();  // Initialize if it's null
    }
    this.imageUrl.clear();
    this.imageUrl.add(profileImageUrl);
  }
}
