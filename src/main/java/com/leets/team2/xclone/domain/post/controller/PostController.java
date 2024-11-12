package com.leets.team2.xclone.domain.post.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.auth.MemberContext;
import com.leets.team2.xclone.common.auth.annotations.UseGuards;
import com.leets.team2.xclone.common.auth.guards.MemberGuard;
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

    @UseGuards(MemberGuard.class)
    @PostMapping("/create")//게시물 생성
    public ResponseEntity<ApiData<PostResponseDTO>> createPost(@RequestPart @Valid PostRequestDTO postRequestDTO,
                                                               @RequestPart(value="images",required = false)List<MultipartFile> imageFiles) throws IOException {
        Member author= MemberContext.getMember();
        Post post=postService.createPost(postRequestDTO,author,imageFiles);
        return ApiData.created(postService.toPostResponseDTO(post));
    }

    @UseGuards(MemberGuard.class)
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiData<PostEditResponseDTO>> updatePost(@PathVariable Long postId,
                                                                   @RequestPart @Validated PostEditRequestDTO postEditRequestDTO,
                                                                   @RequestPart(value="images",required = false) List<MultipartFile> images) throws IOException{
        Member currentMember=MemberContext.getMember();
        Post updatedPost=postService.updatePost(postId,postEditRequestDTO,images,currentMember);
        return ApiData.ok(new PostEditResponseDTO(
                updatedPost.getId(),
                updatedPost.getContent(),
                updatedPost.getImageUrls()
        ));
    }

    @UseGuards(MemberGuard.class)
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiData<Void>> deletePost(@PathVariable Long postId){
        Member currentMember=MemberContext.getMember();
        postService.deletePost(postId,currentMember);
        return ApiData.ok(null);
    }

    @GetMapping("/{postId}")//게시물 하나 조회
    public ResponseEntity<ApiData<PostResponseDTO>>getPost(@PathVariable Long postId){
        PostResponseDTO post=postService.getPost(postId);
        return ApiData.ok(post);
    }

    @GetMapping("/posts")//게시물 전체 조회, 댓글은 제외
    public ResponseEntity<ApiData<List<PostResponseDTO>>> getAllPosts(@RequestParam String currentMemberTag){
        List<PostResponseDTO>posts=postService.getAllPosts(currentMemberTag);
        return ApiData.ok(posts);
    }
}
