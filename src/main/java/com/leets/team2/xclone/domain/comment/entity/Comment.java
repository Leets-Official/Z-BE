package com.leets.team2.xclone.domain.comment.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comment")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends AbstractEntity {
    @Column(name="content",nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id",nullable = false)
    private Member author;
}
