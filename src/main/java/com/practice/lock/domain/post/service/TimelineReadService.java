package com.practice.lock.domain.post.service;

import com.practice.lock.domain.post.entity.Timeline;
import com.practice.lock.domain.post.repository.TimelineRepository;
import com.practice.lock.util.CursorRequest;
import com.practice.lock.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineReadService {
    final private TimelineRepository timelineRepository;

    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        var timelines = findAllBy(memberId, cursorRequest);
        var nextKey = timelines.stream()
                .mapToLong(Timeline::getId)
                .min().orElse(CursorRequest.NONE_KEY);
        return new PageCursor<>(cursorRequest.next(nextKey), timelines);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
                    cursorRequest.key(),
                    memberId,
                    cursorRequest.size()
            );
        }

        return timelineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }
}
