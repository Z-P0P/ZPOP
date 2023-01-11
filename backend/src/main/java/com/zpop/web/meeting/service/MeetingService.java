package com.zpop.web.meeting.service;


import com.zpop.web.meeting.dto.MeetingResponse;
import com.zpop.web.meeting.entity.Meeting;
import com.zpop.web.meeting.repository.MeetingRepository;
import com.zpop.web.member.entity.Member;
import com.zpop.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository repository;

    private final MemberRepository memberRepository;

    @Transactional
    public MeetingResponse getDetail(int id, Integer memberId) {

        Meeting meeting = repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다")
        );

        repository.increaseViewCount(id);

        // memberId 유무 (로그인 여부)에 따라서 member 엔티티를 꺼낸다.
        // 여기서 꺼낸 member는 모임이 해당 member가 주최한 모임인지, 참여한 모임인지 확인하기 위해 사용한다.
        Member member = null;
        if(memberId != null) {
            Optional<Member> foundMember = memberRepository.findById(memberId);
            if(!foundMember.isEmpty())
                member = foundMember.get();
        }

        return MeetingResponse.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .startedAt(meeting.getStartedAt())
                .detailRegion(meeting.getDetailRegion())
                .content(meeting.getContent())
                .category(meeting.getCategory().getType().getValue())
                .region(meeting.getRegion().getType().getValue())
                .maxMember(meeting.getMaxMember())
                .genderCategory(meeting.getGenderCategory().getValue())
                .ageRange(meeting.getAgeRange().getType().getValue())
                .regMemberId(meeting.getMember().getId())
                .viewCount(meeting.getViewCount())
                .commentCount(meeting.getCommentCount())
                .isMyMeeting(meeting.isMyMeeting(member))
                .hasParticipated(meeting.hasParticipated(member))
                .isClosed(meeting.isClosed())
                .build();
    }
}
