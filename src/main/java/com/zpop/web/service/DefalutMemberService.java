package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.MemberEvalDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.MemberEval;
import com.zpop.web.entity.member.MyMeetingView;
import com.zpop.web.utils.TextDateTimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DefalutMemberService implements MemberService {

	@Autowired
    private MemberDao dao;   
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private ParticipationDao participationDao;
    @Autowired
    private MemberEvalDao memberEvalDao;


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

            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

            boolean isClosedResult = false;
            if(m.getClosedAt() != null)
                isClosedResult = true;

            Date date = m.getStartedAt();
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
            LocalDate now =LocalDate.now();
            boolean canRate = now.isAfter(localDate);

            MyMeetingResponse mt = new MyMeetingResponse(
                    m.getCategoryName(),
                    m.getRegionName(),
                    dateTime,
                    m.getTitle(),
                    m.getAge(),
                    m.getMaxMember(),
                    genderCategory,
                    isClosedResult,
                    m.getViewCount(),
                    m.getCommentCount(),
                    m.getMeetingId(),
                    m.getParticipantId(),
                    m.getRegMemberId(),
                    canRate
            );

            
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

            String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

            boolean isClosedResult = false;
            if(m.getClosedAt() != null)
                isClosedResult = true;

            Date date = m.getStartedAt();
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
            LocalDate now =LocalDate.now();
            boolean canRate = now.isAfter(localDate);

            MyMeetingResponse mt = new MyMeetingResponse(
                    m.getCategoryName(),
                    m.getRegionName(),
                    dateTime,
                    m.getTitle(),
                    m.getAge(),
                    m.getMaxMember(),
                    genderCategory,
                    isClosedResult,
                    m.getViewCount(),
                    m.getCommentCount(),
                    m.getMeetingId(),
                    m.getParticipantId(),
                    m.getRegMemberId(),
                    canRate
            );
            
            list.add(mt);
        }

        return list;
    }

    @Override
    public List<EvalMemberDto> getEvalMember(int meetingId) {
       
        return  dao.getEvalMember(meetingId);
    }

    @Override
    @Transactional
    public Map<String, Object> getRateData(EvalDto dto) {
        List<MemberEval> evals = dto.getEvals();
        for (MemberEval eval : evals) {
            eval.setEvaluatorId(dto.getEvaluatorId());
            eval.setMeetingId(dto.getMeetingId());
         }
        memberEvalDao.insertAll(evals);
        participationDao.updateHasEvaluated(dto.getMeetingId(), dto.getEvaluatorId());
        System.out.println(evals);
        dao.updateFameAll(evals);
        return null;
    }

    
}
