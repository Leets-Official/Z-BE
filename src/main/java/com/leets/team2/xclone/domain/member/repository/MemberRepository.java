package com.leets.team2.xclone.domain.member.repository;

import com.leets.team2.xclone.domain.member.entities.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByNicknameAndKakaoId(String nickname, Long kakaoId);
  Optional<Member> findByNicknameAndKakaoId(String nickname, Long kakaoId);
  boolean existsByTag(String tag);
  Optional<Member> findByTag(String tag);

  boolean existsByKakaoId(Long kakaoId);
  Optional<Member> findByKakaoId(Long kakaoId);

  List<Member> findByTagContaining(String tag);
}
