package com.leets.team2.xclone.domain.post.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.domain.post.dto.*;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/create")//게시물 생성
    public ResponseEntity<ApiData<PostResponseDTO>> createPost(@RequestParam String authorTag,
                                                               @RequestPart @Valid PostRequestDTO postRequestDTO,
                                                               @RequestPart(value="images",required = false)List<MultipartFile> imageFiles) throws IOException {
        Member author=memberService.findMemberByTag(authorTag);
        Post post=postService.createPost(postRequestDTO,author,imageFiles);
        return ApiData.created(postService.toPostResponseDTO(post));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiData<PostEditResponseDTO>> updatePost(@PathVariable Long postId,
                                                                   @RequestPart @Validated PostEditRequestDTO postEditRequestDTO,
                                                                   @RequestPart(value="images",required = false) List<MultipartFile> images,
                                                                   @RequestParam String currentMemberTag) throws IOException{
        Member currentMember=memberService.findMemberByTag(currentMemberTag);
        Post updatedPost=postService.updatePost(postId,postEditRequestDTO,images,currentMember);
        return ApiData.ok(new PostEditResponseDTO(
                updatedPost.getId(),
                updatedPost.getContent(),
                updatedPost.getImageUrls()
        ));
    }

    @DeleteMapping("/{postId}")
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

    @GetMapping("/posts")//게시물 전체 조회, 댓글은 제외
    public ResponseEntity<ApiData<List<PostResponseDTO>>> getAllPosts(){
        List<PostResponseDTO>posts=postService.getAllPosts();
        return ApiData.ok(posts);
    }
}
