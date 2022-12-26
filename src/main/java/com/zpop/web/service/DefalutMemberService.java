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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public List<MyMeetingResponse> getMyMeeting(int memberId) {


        List<MyMeetingView> mmv = dao.getMyMeeting(memberId);
        for (MyMeetingView m : mmv) {
            Date date = new Date();
            date = m.getStartedAt();
            LocalDate ldate = new java.sql.Date(date.getTime()).toLocalDate();
            System.out.println("변환용"+ldate);
            LocalDate now =LocalDate.now();
            System.out.println("현재날짜는"+now);

            System.out.println("시작날짜는"+date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,1);
            System.out.println("캘린더"+cal);
            System.out.println("현재날짜가 시작날짜보다 크니?");

            boolean canRate = now.isAfter(ldate);

        }

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
            System.out.println(m.getRegionName());
            MyMeetingResponse mt = new MyMeetingResponse(
                    m.getCategoryName(),
                    m.getRegionName(),
                    dateTime,
                    m.getTitle(),
                    m.getAge(),
                    m.getMaxMember(),
                    genderCategory,
                    m.isClosed(),
                    m.getViewCount(),
                    m.getCommentCount(),
                    m.getMeetingId(),
                    m.getParticipantId(),
                    m.getRegMemberId()

            );
            System.out.println(mt);
            list.add(mt);

        }

        return list;

    }
    @Override
    public List<MyMeetingResponse> getMyGathering(int memberId) {


        List<MyMeetingView> mmv = dao.getMyGathering(memberId);


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
            System.out.println(m.getRegionName());
            MyMeetingResponse mt = new MyMeetingResponse(
                    m.getCategoryName(),
                    m.getRegionName(),
                    dateTime,
                    m.getTitle(),
                    m.getAge(),
                    m.getMaxMember(),
                    genderCategory,
                    m.isClosed(),
                    m.getViewCount(),
                    m.getCommentCount(),
                    m.getMeetingId(),
                    m.getParticipantId(),
                    m.getRegMemberId()

            );
            System.out.println(mt);
            list.add(mt);

        }

        return list;

    }
}

