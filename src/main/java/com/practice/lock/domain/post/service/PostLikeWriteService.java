package com.practice.lock.domain.post.service;

import com.practice.lock.domain.member.dto.MemberDto;
import com.practice.lock.domain.post.entity.Post;
import com.practice.lock.domain.post.entity.PostLike;
import com.practice.lock.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {
    final private PostLikeRepository postLikeRepository;

    public void create(Post post, MemberDto memberDto) {
        var postLike = PostLike.builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();
        postLikeRepository.save(postLike);
    }
}
