package com.leets.team2.xclone.domain.message.service;

import com.leets.team2.xclone.domain.message.repository.messageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class messageService {
    private final messageRepository messageRepository;
}
