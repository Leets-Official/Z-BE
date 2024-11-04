package com.leets.team2.xclone.domain.follow.controller;

import com.leets.team2.xclone.common.ApiData;
import com.leets.team2.xclone.domain.follow.dto.FollowDTO;
import com.leets.team2.xclone.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;
    @GetMapping("/followers")
    public ResponseEntity<ApiData<List<FollowDTO.Response>>> getFollowersByTag(@RequestParam String tag){
        List<FollowDTO.Response> followers = followService.getFollowers(tag);
        return ApiData.ok(followers);
    }
}
