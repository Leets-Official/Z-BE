package com.leets.team2.xclone.domain.follow.service;

import com.leets.team2.xclone.domain.follow.dto.FollowDTO;
import com.leets.team2.xclone.domain.follow.entity.Follow;
import com.leets.team2.xclone.domain.follow.repository.FollowRepository;
import com.leets.team2.xclone.domain.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

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
}
