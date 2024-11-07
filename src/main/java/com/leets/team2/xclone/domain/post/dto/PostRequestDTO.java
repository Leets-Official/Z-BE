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
public class PostRequestDTO {//게시물 생성용

    @NotBlank
    private String content;

    private String imageUrl;

    private Long parentPostId;
}
