package com.leets.team2.xclone.domain.follow.service;

import com.leets.team2.xclone.domain.follow.dto.FollowDTO;
import com.leets.team2.xclone.domain.follow.entity.Follow;
import com.leets.team2.xclone.domain.follow.repository.FollowRepository;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public List<FollowDTO.Response> getFollowers(String tag){
        List<Follow> followers = followRepository.findByFollowee_Tag(tag);
        return followers.stream()
                .map(follow -> {
                    Member follower = follow.getFollower();
                    return FollowDTO.Response.builder()
                            .id(follower.getId())
                            .tag(follower.getTag())
                            .nickname(follower.getNickname())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void followUser(FollowDTO.Save dto, String myTag) {
        Member followee = memberRepository.findByTag(dto.tag()).orElseThrow(NoSuchMemberException::new);
        Member follower = memberRepository.findByTag(myTag).orElseThrow(NoSuchMemberException::new);
        Follow followInfo = Follow.builder()
                        .follower(follower)
                        .followee(followee)
                        .build();
        followRepository.save(followInfo);
    }
}
