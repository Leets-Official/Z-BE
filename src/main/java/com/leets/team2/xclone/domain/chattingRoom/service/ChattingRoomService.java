package com.leets.team2.xclone.domain.chattingRoom.service;

import com.leets.team2.xclone.domain.chattingRoom.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
}
