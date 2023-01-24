package com.zpop.web.service;

import java.util.List;
import java.util.Map;

import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.dto.ProfileResponse;
import com.zpop.web.entity.Member;

public interface MemberService {

        Member getById(int id);
        List<MyMeetingResponse> getMyMeeting(int memberId);
        List<MyMeetingResponse> getMyGathering(int memberId);
        List<EvalMemberDto> getEvalMember(int meetingId);
        Map<String,Object> getRateData(EvalDto dto);
        ProfileResponse getParticipant(int id);

        Map<String, Object> checkNicknameValid(String nickname);
        Member updateNickname(int memberId, String nickname);
}

