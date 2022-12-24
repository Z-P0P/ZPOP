package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.member.MyMeetingView;
import com.zpop.web.utils.TextDateTimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefalutMemberService implements MemberService{

    @Autowired
    private MemberDao dao;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private ParticipationDao participationDao;


    public DefalutMemberService() {
    }

    public DefalutMemberService(MemberDao dao, MeetingDao meetingDao, ParticipationDao participationDao) {
        this.dao = dao;
        this.meetingDao = meetingDao;
        this.participationDao = participationDao;
    }


    @Override
    public Member getById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<MyMeetingView> getMyMeeting(int memberId) {

        List<MyMeetingView> list = dao.getMyMeeting(memberId);
        for(MyMeetingView m : list) {
            String genderCategory = "누구나";
            switch (m.getGenderCategory()){
                case 1:
                    genderCategory = "남자 모임";
                    break;
                case 2:
                    genderCategory = "여자 모임";
                    break;
            }
            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

            List<MyMeetingResponse> list2 = new ArrayList<>();

            //        }
//
//        MyMeetingView


    }

        return list;
    }

    @Override
    public List<MyMeetingView> getMyGathering(int memberId) {
        return null;
    }


}
