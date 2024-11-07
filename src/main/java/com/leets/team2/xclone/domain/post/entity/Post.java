package com.leets.team2.xclone.domain.post.entity;

import com.leets.team2.xclone.common.entity.AbstractEntity;
import com.leets.team2.xclone.domain.member.entities.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Post extends AbstractEntity {//정말 기본적인 부분들만 일단 만들어냈습니다.

    @Column(name="content",nullable = false)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)//Member랑 다대일
    @JoinColumn(name="author_id",nullable = false)//author_id로 Member와 연결
    private Member author;//작성자를 멤버 객체로.

    @Column(name="image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_post_id")
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost")
    private List<Post> childPosts=new ArrayList<>();

}
