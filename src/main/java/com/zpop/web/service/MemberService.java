package com.zpop.web.service;

import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.member.MyMeetingView;

import java.util.List;

public interface MemberService {

        Member getById(int id);

        List<MyMeetingResponse> getMyMeeting(int memberId);

        List<MyMeetingResponse> getMyGathering(int memberId);


        //평가할 모임을 위해 모임아이디 저장할 함수
        List<MyMeetingView> getMeeting(int meetingId);



}

