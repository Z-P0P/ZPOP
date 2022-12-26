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
public class DefalutMemberService implements MemberService {

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
    public List<MyMeetingResponse> getTest(int memberId) {


        List<MyMeetingView> mmv = dao.getMyMeeting(memberId);


        List<MyMeetingResponse> list = new ArrayList<>();

        for (MyMeetingView m : mmv) {
            String genderCategory = "누구나";
            switch (m.getGenderCategory()) {
                case 1:
                    genderCategory = "남자 모임";
                    break;
                case 2:
                    genderCategory = "여자 모임";
                    break;
            }
            System.out.println(m.toString());

            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

            MyMeetingResponse mt = new MyMeetingResponse(
                    m.getCategoryName(),
                    m.getRegionName(),
                    dateTime,
                    m.getTitle(),
                    m.getAge(),
                    m.getMaxMember(),
                    genderCategory,
                    m.getIsClosed(),
                    m.getViewCount(),
                    m.getCommentCount(),
                    m.getMeetingId(),
                    m.getParticipantId(),
                    m.getRegMemberId()
            );
            System.out.println(mt);
            list.add(mt);

        }

        for (MyMeetingResponse mt: list
        ) {
            System.out.println("test");
            System.out.println(mt.getAge());
            System.out.println(mt.getCategoryName());
        }
        return list;

    }

    @Override
    public List<MyMeetingView> getMyGathering(int memberId) {
        return null;
    }
}
//    @Override
//    public List<MyMeetingResponse> getList(int memberId) {
//
//        List<MyMeetingView> myMeetingViews = dao.getMyMeeting(id);
//
//    }

//    public List<MyMeetingView> getMyMeeting(int memberId) {
//
//        List<MyMeeting> list1 = dao.getMyMeeting(memberId);
//        for(MyMeetingView m : list1) {
//            String genderCategory = "누구나";
//            switch (m.getGenderCategory()){
//                case 1:
//                    genderCategory = "남자 모임";
//                    break;
//                case 2:
//                    genderCategory = "여자 모임";
//                    break;
//            }
//
//            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());
//
//            MyMeetingResponse list2 = new MyMeetingResponse(
//                    m.getCategoryName(),
//                    m.getRegionName(),
//                    dateTime,
//                    m.getTitle(),
//                    m.getAge(),
//                    m.getMaxMember(),
//                    genderCategory,
//                    m.getIsClosed(),
//                    m.getViewCount(),
//                    m.getCommentCount(),
//                    m.getMeetingId(),
//                    m.getParticipantId(),
//                    m.getRegMemberId()
//            );
//            list1.add(list2);
            //        }
//
//        MyMeetingView
//
//
//    }
//
//        return list1;
//    }

//    @Override
//    public List<MyMeetingView> getMyGathering(int memberId) {
//        return null;
//    }
//
//
//}
