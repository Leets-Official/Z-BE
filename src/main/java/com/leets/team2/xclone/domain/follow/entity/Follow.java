package com.leets.team2.xclone.domain.follow.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Follow extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;    // 팔로잉 하는 멤버

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private Member followee;    // 팔로잉 대상
}
