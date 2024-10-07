package com.leets.team2.xclone.domain.member.repository;

import com.leets.team2.xclone.domain.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
