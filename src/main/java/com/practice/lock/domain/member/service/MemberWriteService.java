package com.practice.lock.domain.member.service;

import com.practice.lock.domain.member.dto.RegisterMemberCommand;
import com.practice.lock.domain.member.entity.Member;
import com.practice.lock.domain.member.entity.MemberNicknameHistory;
import com.practice.lock.domain.member.repository.MemberNicknameHistoryRepository;
import com.practice.lock.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @Transactional
    public Member create(RegisterMemberCommand command) {
        var member = Member.builder()
                .email(command.email())
                .nickname(command.nickname())
                .birthday(command.birthday())
                .build();
        var savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    @Transactional
    public void changeNickname(Long memberId, String nickname) {
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        var savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

}
