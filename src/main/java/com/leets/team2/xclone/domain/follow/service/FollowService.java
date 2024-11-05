package com.leets.team2.xclone.domain.follow.service;

import com.leets.team2.xclone.domain.follow.dto.FollowDTO;
import com.leets.team2.xclone.domain.follow.entity.Follow;
import com.leets.team2.xclone.domain.follow.repository.FollowRepository;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.repository.MemberRepository;
import com.leets.team2.xclone.exception.FollowAlreadyExistsException;
import com.leets.team2.xclone.exception.InvalidFollowException;
import com.leets.team2.xclone.exception.NoSuchFollowException;
import com.leets.team2.xclone.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public List<FollowDTO.Response> getFollowers(String tag){
        List<Follow> followers = followRepository.findByFollowee_Tag(tag);
        return followers.stream()
                .map(FollowDTO::followerMemberToResponse)
                .toList();
    }

    public List<FollowDTO.Response> getFollowings(String tag) {
        List<Follow> followings = followRepository.findByFollower_Tag(tag);
        return followings.stream()
                .map(FollowDTO::followingMemberToResponse)
                .toList();
    }

    @Transactional
    public void followUser(FollowDTO.Save dto, String myTag) {
        Member followee = memberRepository.findByTag(dto.tag()).orElseThrow(NoSuchMemberException::new);
        Member follower = memberRepository.findByTag(myTag).orElseThrow(NoSuchMemberException::new);

        Follow follows = followRepository.findByFollowee_TagAndFollower_Tag(dto.tag(), myTag);
        checkValidFollow(follows, 0);

        Follow followInfo = Follow.builder()
                        .follower(follower)
                        .followee(followee)
                        .build();
        followRepository.save(followInfo);
    }

    @Transactional
    public void unfollowUser(FollowDTO.Save dto, String myTag) {
        Member followee = memberRepository.findByTag(dto.tag()).orElseThrow(NoSuchMemberException::new);
        Member follower = memberRepository.findByTag(myTag).orElseThrow(NoSuchMemberException::new);

        Follow followInfo = followRepository.findByFollowee_TagAndFollower_Tag(dto.tag(), myTag);
        checkValidFollow(followInfo, 1);
        followRepository.delete(followInfo);
    }

    // option
    // 0 : follow
    // 1 : unfollow
    private void checkValidFollow(Follow followInfo, int option){
        if(followInfo.getFollower().equals(followInfo.getFollowee())){
            throw new InvalidFollowException(); // 자기 자신을 팔로우/언팔로우한 경우
        }
        if (!(followInfo ==null) &option==0) {
            throw new FollowAlreadyExistsException();   // 이미 팔로우한 경우
        }
        if(followInfo==null&&option==1){
            throw new NoSuchFollowException();  // 팔로우하지 않은 대상을 언팔로우 하려는 경우
        }
    }

}
