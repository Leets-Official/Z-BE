package com.leets.team2.xclone.domain.post.repository;

import com.leets.team2.xclone.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
