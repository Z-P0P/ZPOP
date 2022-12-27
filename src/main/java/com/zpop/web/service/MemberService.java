package com.zpop.web.service;

import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;

import java.util.List;

public interface MemberService {

        //0. 회원 인가 진행
        //0. 세션에서 회원아이디 꺼내오기.

        //1. 회원 아이디를 파라미터에 넣어주고 회원 정보를 받아온다.
        Member getById(int id);
//        List<MeetingThumbnailView> getMyMeeting(int memberId);

        //2. 회원이 참가한 모임을 보여준다.

//        List<MyMeetingView> getMyMeeting(int memberId);

        List<MyMeetingResponse> getMyMeeting(int memberId);

        List<MyMeetingResponse> getMyGathering(int memberId);
    //3. 회원이 모집한 모임을 보여준다.

        //4. 회원 탈퇴

        //5. 프로필 수정시, 프로필 사진 및 닉네임 변경


        
	public List<Member> getList(int page, String keyword, String option);

	public int getSearchCount(String keyword, String option);
	
}

