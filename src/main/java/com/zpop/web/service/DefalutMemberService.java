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
            System.out.println(mt);
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
    public Map<String, Object> getEvalData(EvalDto dto) {

//        ArrayList<Integer> rateArr = (ArrayList)data.get("rateValue");
//        ArrayList<Integer> evaluateeArr = (ArrayList)data.get("evaluateeId");
//        Integer meetingId = (Integer) data.get("meetingId");

//        String evaluatorId = (String) data.get("evaluatorId");
   
//                evaluateeId : participantsId,
//                evalId : evalId,
//                rateValue : rateValue,
//                evaluatorId : memberId,
//                meetingId : meetingId
//      == insert list 만들어서 insert
        List<MemberEval> evals = dto.getEvals();


        //MemberEval list 에서 값을 하나씩 채워넣는다 meetingId, evaluatorId

        for (MemberEval eval : evals
             ) {
            eval.setEvaluatorId(dto.getEvaluatorId());
            eval.setMeetingId(dto.getMeetingId());
        }
        memberEvalDao.insertAll(evals);
        participationDao.updateHasEvaluated(dto.getMeetingId(), dto.getEvaluatorId());
        System.out.println(evals);
        dao.updateFameAll(evals);

//        int size = evaluateeArr.size();
//        for (int i =0; i<size; i++) {
//
//            int evaluateeId = evaluateeArr.get(i);
//            //점수값
//            int result = 0;
//            int rateValue = rateArr.get(i);
//            if (rateValue == 0){
//                result = -1;
//            }else if (rateValue == 50){
//                result = 1;
//            }else {
//                result = 3;
//            }
//
//            MemberEval eval = new MemberEval(meetingId,evaluatorId,evaluateeId,result);
//
//            evals.add(eval);
//            System.out.println(eval);
//            System.out.println("평가대상"+evaluateeId);
//            System.out.println("평가값"+rateValue);
//
//        }
//        System.out.println(evals);

////        System.out.println(evalIdArr.toArray());
//        for (Integer a :evaluateeArr
//        ) {
//            System.out.println(a);
//
//        }



//        for(int i = 0; i<size; i++) {
//
//        }

        // for (Integer b :evalIdArr
        // ) {
        //     System.out.println(b);
        //    int i = b.intValue();
        //     System.out.println("갯수"+evalIdArr.size());
        //     System.out.println("클래스"+b.getClass());
        //     System.out.println("인트"+i);
        // }

    


        // String meetingId = data.get("meetingId").toString();
        // String evaluatorId =data.get("evaluatorId").toString();

        // System.out.println(meetingId); //59
        // System.out.println(evaluatorId);//8





//        Integer evaluatorId = (Integer) data.get("evaluatorId");


//        System.out.println(arr2);
//        System.out.println( arr2.get(0));

//        int size;
//
//        for (String a :evaluateeArr
//             ) {
//            System.out.println(a);
//            size = a.length();
//        }


//        for (int i =0; i<size; i++) {
//            String meetingId = (String) data.get("meetingId");
//            String rateValue = (String) data.get("rateValue");
//            String evaluatorId = (String) data.get("evaluatorId");
////            String
//
//        }

   
    
        
   
      

        // Itera
        // for(Map<String,Object> m : data){

        // }
        //평가자 아이디8 피평가자 아이디 5 result =1 참여 

        
        return null;
    }

    
}
