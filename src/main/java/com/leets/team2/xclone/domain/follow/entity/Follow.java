package com.leets.team2.xclone.domain.follow.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
@Table
public class Follow extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;    // 팔로잉 하는 멤버

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private Member followee;    // 팔로잉 대상
}
