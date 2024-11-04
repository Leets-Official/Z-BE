package com.leets.team2.xclone.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {//생성 및 수정에 데이터 전달시 사용
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String imageUrl;
}
