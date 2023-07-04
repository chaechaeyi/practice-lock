package com.practice.lock.application.usacase;

import com.practice.lock.domain.follow.entity.Follow;
import com.practice.lock.domain.follow.service.FollowReadService;
import com.practice.lock.domain.post.dto.PostDto;
import com.practice.lock.domain.post.entity.Timeline;
import com.practice.lock.domain.post.service.PostReadService;
import com.practice.lock.domain.post.service.TimelineReadService;
import com.practice.lock.util.CursorRequest;
import com.practice.lock.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecase {
    final private FollowReadService followReadService;

    final private PostReadService postReadService;

    final private TimelineReadService timelineReadService;

    public PageCursor<PostDto> execute(Long memberId, CursorRequest cursorRequest) {
        var follows = followReadService.getFollowings(memberId);
        var followerMemberIds = follows
                .stream()
                .map(Follow::getToMemberId)
                .toList();

        return postReadService.getPostDtos(followerMemberIds, cursorRequest);
    }

    public PageCursor<PostDto> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPostDtos(postIds);
        return new PageCursor<>(pagedTimelines.nextCursorRequest(), posts);
    }
}
