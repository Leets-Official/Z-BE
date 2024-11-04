package com.leets.team2.xclone.domain.post.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.domain.post.dto.PostDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/create")//게시물 생성
    public ResponseEntity<ApiData<PostDTO>> createPost(@RequestParam String authorTag, @RequestBody @Validated PostDTO postDTO,
                                                       @RequestPart(value="image",required = false)MultipartFile imageFile) throws IOException {
        Member author=memberService.findMemberByTag(authorTag);
        Post post=postService.createPost(postDTO,author,imageFile);
        return ApiData.created(new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl()
        ));
    }

    @PatchMapping("/{postId}/edit")
    public ResponseEntity<ApiData<PostDTO>> updatePost(@PathVariable Long postId,@RequestBody @Validated PostDTO postDTO,
                                                       @RequestPart(value="image",required = false)MultipartFile imageFile) throws IOException{
        Post updatedPost=postService.updatePost(postId,postDTO,imageFile);
        return ApiData.ok(new PostDTO(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getImageUrl()
        ));
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<ApiData<Void>> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ApiData.ok(null);
    }
}
