package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.domain.post.dto.PostDTO;
import com.leets.team2.xclone.domain.post.entity.Post;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostDTO postDTO, Member author){
        Post post=Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(author)
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(Long postId,PostDTO postDTO){
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new NoSuchElementException("해당 게시물이 없습니다."));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return postRepository.save(post);
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);//해당 게시물 아이디의 게시물 삭제
    }
}
