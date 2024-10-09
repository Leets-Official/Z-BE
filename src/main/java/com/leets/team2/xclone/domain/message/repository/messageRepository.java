package com.leets.team2.xclone.domain.message.repository;

import org.aspectj.bridge.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface messageRepository extends JpaRepository<Message, Long> {
}
