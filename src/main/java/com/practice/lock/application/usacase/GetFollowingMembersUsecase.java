package com.practice.lock.application.usacase;

import com.practice.lock.domain.follow.entity.Follow;
import com.practice.lock.domain.follow.service.FollowReadService;
import com.practice.lock.domain.member.dto.MemberDto;
import com.practice.lock.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {
    final private MemberReadService memberReadService;
    final private FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
