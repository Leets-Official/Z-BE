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
public class PostResponseDTO {//응답으로 보내는 데이터. 데이터 반환 및 결과를 줄 때 사용.

    private Long id;//게시물 id

    @NotBlank
    private String title;//제목

    @NotBlank
    private String content;//내용

    private String imageUrl;//이미지 url

}
