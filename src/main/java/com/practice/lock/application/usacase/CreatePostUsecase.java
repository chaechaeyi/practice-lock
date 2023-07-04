package com.practice.lock.application.usacase;

import com.practice.lock.domain.follow.entity.Follow;
import com.practice.lock.domain.follow.service.FollowReadService;
import com.practice.lock.domain.post.dto.PostCommand;
import com.practice.lock.domain.post.service.PostWriteService;
import com.practice.lock.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    final private PostWriteService postWriteService;
    final private FollowReadService followReadService;
    final private TimelineWriteService timelineWriteService;

    @Transactional
    public Long execute(PostCommand command) {
        var postId = postWriteService.create(command);

        var followerMemberIds = followReadService
                .getFollowers(command.memberId()).stream()
                .map((Follow::getFromMemberId))
                .toList();

        timelineWriteService.deliveryToTimeLine(postId, followerMemberIds);

        return postId;
    }

}
