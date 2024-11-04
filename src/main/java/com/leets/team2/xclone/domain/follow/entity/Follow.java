package com.leets.team2.xclone.domain.follow.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;    // 팔로잉 하는 멤버

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private Member followee;    // 팔로잉 대상

    @Builder
    public Follow(Member follower, Member followee){
        this.follower = follower;
        this.followee = followee;
    }
}
