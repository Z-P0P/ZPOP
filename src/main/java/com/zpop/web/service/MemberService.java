package com.zpop.web.service;

import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;

import java.util.List;

public interface MemberService {

        Member getById(int id);

        List<MyMeetingResponse> getMyMeeting(int memberId);

        List<MyMeetingResponse> getMyGathering(int memberId);
}

