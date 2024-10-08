package com.leets.team2.xclone.domain.follow.repository;

import com.leets.team2.xclone.domain.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRespository extends JpaRepository<Member, Long> {
}
