package com.leets.team2.xclone.domain.post.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.domain.post.dto.PostEditRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostEditResponseDTO;
import com.leets.team2.xclone.domain.post.dto.PostRequestDTO;
import com.leets.team2.xclone.domain.post.dto.PostResponseDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/create")//게시물 생성
    public ResponseEntity<ApiData<PostResponseDTO>> createPost(@RequestParam String authorTag, @RequestBody @Validated PostRequestDTO postRequestDTO,
                                                               @RequestPart(value="image",required = false)MultipartFile imageFile) throws IOException {
        Member author=memberService.findMemberByTag(authorTag);
        Post post=postService.createPost(postRequestDTO,author,imageFile);
        return ApiData.created(postService.toPostResponseDTO(post));
    }

    @PatchMapping("/{postId}/edit")
    public ResponseEntity<ApiData<PostEditResponseDTO>> updatePost(@PathVariable Long postId, @RequestBody @Validated PostEditRequestDTO postEditRequestDTO,
                                                                   @RequestPart(value="image",required = false)MultipartFile imageFile,
                                                                   @RequestParam String currentMemberTag) throws IOException{
        Member currentMember=memberService.findMemberByTag(currentMemberTag);
        Post updatedPost=postService.updatePost(postId,postEditRequestDTO,imageFile,currentMember);
        return ApiData.ok(new PostEditResponseDTO(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getImageUrl()
        ));
    }

    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<ApiData<Void>> deletePost(@PathVariable Long postId,
                                                    @RequestParam String currentMemberTag){
        Member currentMember=memberService.findMemberByTag(currentMemberTag);
        postService.deletePost(postId,currentMember);
        return ApiData.ok(null);
    }

    @GetMapping("/{postId}")//게시물 하나 조회
    public ResponseEntity<ApiData<PostResponseDTO>>getPost(@PathVariable Long postId){
        PostResponseDTO post=postService.getPost(postId);
        return ApiData.ok(post);
    }
}
