package com.leets.team2.xclone.domain.follow.repository;

import com.leets.team2.xclone.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower_Tag(String tag);
    List<Follow> findByFollowee_Tag(String tag);
}
