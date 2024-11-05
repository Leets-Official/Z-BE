package com.leets.team2.xclone.domain.comment.service;

import com.leets.team2.xclone.domain.comment.Repository.CommentRepository;
import com.leets.team2.xclone.domain.comment.entity.Comment;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import com.leets.team2.xclone.exception.CommentNotFoundException;
import com.leets.team2.xclone.exception.PostNotFoundException;
import com.leets.team2.xclone.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;

    public Comment addComment(Long postId,String content,String authorTag){//댓글 추가
        Member author=memberService.findMemberByTag(authorTag);
        Post post=postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        Comment comment=Comment.builder()
                .content(content)
                .post(post)
                .author(author)
                .build();
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId,Member currentMember){
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if(!comment.getAuthor().getId().equals(currentMember.getId())){
            throw new UnauthorizedException();
        }
        commentRepository.delete(comment);
    }
}
