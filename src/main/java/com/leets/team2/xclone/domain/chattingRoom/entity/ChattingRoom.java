package com.leets.team2.xclone.domain.chattingRoom.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chatting_room")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChattingRoom extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "member1_id")
    private Member member1;


    @ManyToOne
    @JoinColumn(name = "member2_id")
    private Member member2;
}
