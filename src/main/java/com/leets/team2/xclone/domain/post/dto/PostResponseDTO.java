package com.leets.team2.xclone.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {//게시물 생성용

    private Long id;//게시물 id

    @NotBlank
    private String title;//제목

    @NotBlank
    private String content;//내용

    private String imageUrl;//이미지 url

    private Long parentPostId;

    private List<PostResponseDTO>childPosts;

}
