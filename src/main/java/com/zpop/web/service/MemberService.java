package com.zpop.web.service;

import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

        Member getById(int id);
        List<MyMeetingResponse> getMyMeeting(int memberId);
        List<MyMeetingResponse> getMyGathering(int memberId);
        List<EvalMemberDto> getEvalMember(int meetingId);
        Map<String,Object> getEvalData(EvalDto dto);

}

