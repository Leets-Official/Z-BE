package com.leets.team2.xclone.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostDTO {
    //DTO 같은 경우 각각의 기능에 따른 DTO를 전부 만들 예정입니다. 다음은 예시 상황입니다. 이런 방식으로 구현하는건 어떻게 생각하시는지 의견 부탁드리겠습니다.
    public record CreatePostRequest(
            @NotNull(message = "아이디는 필수입니다.")
            Long postId,

            @NotBlank(message = "제목은 필수입니다.")
            String title,

            @NotBlank(message = "내용은 필수입니다.")
            String content
    ){}
    public record CreatePostResponse(
            Long postId,
            String title,
            String content
    ){
        public static CreatePostResponse of(Long postId,String title,String content) {
            return new CreatePostResponse(postId,title,content);
        }
    }
}
