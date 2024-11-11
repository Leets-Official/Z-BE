package com.leets.team2.xclone.domain.follow.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.common.auth.MemberContext;
import com.leets.team2.xclone.common.auth.annotations.UseGuards;
import com.leets.team2.xclone.common.auth.guards.MemberGuard;
import com.leets.team2.xclone.domain.follow.dto.FollowDTO;
import com.leets.team2.xclone.domain.follow.service.FollowService;
import com.leets.team2.xclone.domain.member.entities.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;
    @Operation(summary = "특정 유저의 팔로워 목록 조회 API", description = "특정 유저의 팔로워 목록을 조회합니다.")
    @GetMapping("/followers")
    public ResponseEntity<ApiData<List<FollowDTO.Response>>> getFollowersByTag(@RequestParam String tag){
        List<FollowDTO.Response> followers = followService.getFollowers(tag);
        return ApiData.ok(followers);
    }

    @Operation(summary = "특정 유저의 팔로잉 목록 조회 API", description = "특정 유저가 팔로우 중인 목록을 조회합니다.")
    @GetMapping("/followings")
    public ResponseEntity<ApiData<List<FollowDTO.Response>>> getFollowingsByTag(@RequestParam String tag){
        List<FollowDTO.Response> followings = followService.getFollowings(tag);
        return ApiData.ok(followings);
    }


    @UseGuards({MemberGuard.class})
    @Operation(summary = "팔로우 API", description = "사용자가 다른 대상을 팔로우합니다.")
    @PostMapping
    public ResponseEntity<ApiData<Void>> follow(@RequestBody FollowDTO.Save dto){
        Member currentMember = MemberContext.getMember();
        log.warn(currentMember.toString());
        followService.followUser(dto, currentMember);
        return ApiData.ok(null);
    }

    @Operation(summary = "언팔로우 API", description = "사용자가 다른 대상을 언팔로우합니다.")
    @DeleteMapping
    public ResponseEntity<ApiData<Void>> unfollow(@RequestBody FollowDTO.Save dto){
        Member currentMember = MemberContext.getMember();
        followService.unfollowUser(dto, currentMember);
        return ApiData.ok(null);
    }
}
