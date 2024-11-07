package com.leets.team2.xclone.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepostResponseDTO {
    private Long id;

    private String content;

    private String imageUrl;

}
