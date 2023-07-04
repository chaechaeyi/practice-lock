package com.practice.lock.application.usacase;

import com.practice.lock.domain.member.service.MemberReadService;
import com.practice.lock.domain.post.service.PostLikeWriteService;
import com.practice.lock.domain.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostLikeUsacase {
    final private PostReadService postReadService;
    final private MemberReadService memberReadService;
    final private PostLikeWriteService postLikeWriteService;

    public void execute(Long postId, Long memberId) {
        var post = postReadService.getPost(postId);
        var member = memberReadService.getMember(memberId);
        postLikeWriteService.create(post, member);
    }
}
