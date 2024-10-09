package com.leets.team2.xclone.domain.message.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.chattingRoom.entity.ChattingRoom;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class message extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member sentUser;

    @ManyToOne
    @JoinColumn(name = "chat_room")
    private ChattingRoom chatRoom;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean seen;

    @PrePersist
    public void prePersist() {
        seen = false;
    }

}
