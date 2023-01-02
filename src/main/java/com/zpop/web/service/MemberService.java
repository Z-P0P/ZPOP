package com.zpop.web.service;

import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.member.MyMeetingView;

import java.util.List;

public interface MemberService {

        Member getById(int id);

        List<MyMeetingResponse> getMyMeeting(int memberId);

        List<MyMeetingResponse> getMyGathering(int memberId);

        List<MyMeetingView> getParticipant(int meetingId);

        List<EvalMemberDto> getEvalMember(int meetingId);
}

