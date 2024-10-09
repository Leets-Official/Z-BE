package com.leets.team2.xclone.domain.chattingRoom.repository;

import com.leets.team2.xclone.domain.chattingRoom.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
}
