package com.leets.team2.xclone.domain.post.service;

import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
}
