package com.leets.team2.xclone.domain.comment.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.comment.dto.CommentRequestDTO;
import com.leets.team2.xclone.domain.comment.dto.CommentResponseDTO;
import com.leets.team2.xclone.domain.comment.entity.Comment;
import com.leets.team2.xclone.domain.comment.service.CommentService;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/{postId}/create")
    public ResponseEntity<ApiData<CommentResponseDTO>> createComment(@PathVariable Long postId,
                                                                     @RequestParam String authorTag,
                                                                     @RequestBody @Validated CommentRequestDTO commentRequestDTO){
        Comment comment= commentService.addComment(postId,commentRequestDTO.getContent(),authorTag);
        return ApiData.created(new CommentResponseDTO(comment.getId(),comment.getContent()));
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<ApiData<Void>> deleteComment(@PathVariable Long commentId,
                                                       @RequestParam String currentMemberTag){
        Member currentMember=memberService.findMemberByTag(currentMemberTag);
        commentService.deleteComment(commentId,currentMember);
        return ApiData.ok(null);
    }

}
