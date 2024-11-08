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
public class RepostResponseDTO {
    private Long id;

    @NotBlank
    private String authorNickname;//작성자 닉네임

    @NotBlank
    private String authorTag;//작성자 태그

    private String content;

    private List<String> imageUrls;

}
