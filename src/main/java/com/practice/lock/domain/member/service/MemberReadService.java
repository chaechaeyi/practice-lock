package com.practice.lock.domain.member.service;

import com.practice.lock.domain.member.dto.MemberDto;
import com.practice.lock.domain.member.dto.MemberNicknameHistoryDto;
import com.practice.lock.domain.member.entity.Member;
import com.practice.lock.domain.member.entity.MemberNicknameHistory;
import com.practice.lock.domain.member.repository.MemberNicknameHistoryRepository;
import com.practice.lock.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {
    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long memberId) {
        var member = memberRepository.findById(memberId).orElseThrow();
        return toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> memberIds) {
        var members = memberRepository.findAllByIdIn(memberIds);
        return members.stream()
                .map(this::toDto)
                .toList();
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        var histories = memberNicknameHistoryRepository.findAllByMemberId(memberId);
        return histories.stream()
                .map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getNickname(), member.getEmail(), member.getBirthday());
    }

    public MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );

    }
}
