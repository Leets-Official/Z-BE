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
public class PostDTO {

    private Long id;//게시물 id

    @NotBlank
    private String title;//제목

    @NotBlank
    private String content;//내용

}
