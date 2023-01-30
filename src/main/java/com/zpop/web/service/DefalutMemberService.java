package com.zpop.web.service;

import com.zpop.web.dao.*;
import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.dto.ProfileResponse;
import com.zpop.web.entity.*;
import com.zpop.web.entity.member.MyMeetingView;
import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;
import com.zpop.web.utils.TextDateTimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Pattern;

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

    @Autowired
    private  ProfileFileDao profileFileDao;
    @Autowired
    private NicknameLogDao nicknameLogDao;

    private final int MAX_NICKNAME_LENGTH = 10;
	private final String nicknamePattern = "^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$";

    public DefalutMemberService() {
    }

    public DefalutMemberService(MemberDao dao, MeetingDao meetingDao,
                                ParticipationDao participationDao,
                                ProfileFileDao profileFileDao) {
        this.dao = dao;
        this.meetingDao = meetingDao;
        this.participationDao = participationDao;
        this.profileFileDao = profileFileDao;
    }


    /***
     * 회원 기본정보 응답
     * @param id
     * @return
     */
    @Override
    public Member getById(int id) {
        return dao.getById(id);
    }

    /**
     * 내가 참여한 모임 응답
     * @param memberId
     * @return
     */
    @Override
    public List<MyMeetingResponse> getMyMeeting(int memberId) {
        List<MyMeetingView> mmv = dao.getMyMeeting(memberId);

        List<MyMeetingResponse> list = new ArrayList<>();

        for (MyMeetingView m : mmv) {
            String genderCategory = "남녀 모두";
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
            boolean isEvaluated;
            if(m.getHasEvaluated() == 1) {
                isEvaluated = true;
            }else {
                isEvaluated = false;
            }


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
                    canRate,
                    isEvaluated
            );

            
            list.add(mt);

        }

        return list;

    }


    /***
     * 내가 모집한 모임 응답
     * @param memberId
     * @return
     */
    @Override
    public List<MyMeetingResponse> getMyGathering(int memberId) {

        List<MyMeetingView> mmv = dao.getMyGathering(memberId);

        List<MyMeetingResponse> list = new ArrayList<>();

        for (MyMeetingView m : mmv) {
            String genderCategory = "남녀 모두";
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

            boolean isEvaluated;
            if(m.getHasEvaluated() == 1) {
                isEvaluated = true;
            }else {
                isEvaluated = false;
            }

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
                    canRate,
                    isEvaluated
            );

            list.add(mt);
        }

        return list;
    }

    /***
     * TODO : 주최자 혼자인채로 모임이 마감된 경우, 평가하기 비활성화
     * 평가하기 클릭시, 해당 모임에 참여한 회원을 불러옵니다.
     * @param meetingId
     * @return
     */
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

        int  result = participationDao.updateHasEvaluated(dto.getMeetingId(), dto.getEvaluatorId());
        System.out.println(result);
        dao.updateFameAll(evals);
        return null;
    }

    @Override
    public ProfileResponse getProfile(int id) {

        Member member = getById(id);
        if(member==null)
            throw new CustomException(ExceptionReason.NOT_FOUND_MEMBER);

        if(member.getResignedAt() != null) {
            return new ProfileResponse(
                    0,
                    "",
                    0,
                    "",
                    true,
                    0
            );
        }
        // 해당 사용자의 모임 참여 횟수 조회
        int participatedCount = 0;
        List<Participation> participationList = participationDao.getListByParticipantId(id);

        for(Participation p : participationList) {
            if( p.getBannedAt()==null && p.getCanceledAt()==null )
                participatedCount++;
        }

        return new ProfileResponse(
                member.getId(),
                member.getNickname(),
                member.getFame(),
                member.getProfileImagePath(),
                false,
                participatedCount
        );
    }


    /**
     * 닉네임 유효성 검사 (중복, 유효한 닉네임인지)
     * @param nickname
     * @return
     */
    public Map<String, Object> checkNicknameValid(String nickname) {
		Map<String, Object> result = new HashMap<>();
		boolean isRegexMatch = Pattern.matches(nicknamePattern, nickname);
		
		if (nickname.isBlank() || nickname.isEmpty() || 
				nickname.length() > MAX_NICKNAME_LENGTH
				|| !isRegexMatch) {
			result.put("result", "FORMAT_NOT_ALLOWED");
			return result;
		} 
		
		Member member = dao.getByNickname(nickname);
		if (member != null) {
			result.put("result", "NICKNAME_ALREADY_USED");
			return result;
		}
		
		result.put("result", "NICKNAME_VALID");
		return result;
	}

    /**
     * 닉네임 변경 -- TODO : 변경할 수 없을 경우 클라이언트에게 응답 해주어야함
     * @param memberId
     * @param nickname
     * @return
     */
	@Transactional
	@Override
	public int updateNickname(int memberId, String nickname) {

        //멤버의 아이디를 통해 닉네임 로그 중 가장 최근 기록 하나를 불러옵니다.
         NicknameLog nicknameLog = nicknameLogDao.getLatestByMemberId(memberId);
       
         //해당 기록의 날짜를 얻습니다.
        Date date = nicknameLog.getCreatedAt();

        //해당 기록의 날짜와 현재날짜의 차이를 비교합니다.
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        LocalDate now =LocalDate.now();

        //Period 타입은 P1Y2M33D 같은형식을 반환.
        Period period = Period.between(localDate, now);

        //조건 처리결과 period.getmonths -> "월"의차이만 비교
        //ex) 2022-11월과 2023-11월비교 -> 0
        //따라서 year를 추가하여 연도도 조건에 추가
        int year = period.getYears();
        int month = period.getMonths();
        System.out.println(month);

        int result;
         //날짜의 차이가 30일 이내일 경우
         if (year<0 || month==0){
            //변경할 수 없음을 클라이언트에게 알려주기
            System.out.println("30일 이내에는 변경 불가함");
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "30일 이내에는 닉네임 변경이 불가합니다.");
         }else {

            //닉네임 유효성 확인, 닉네임 중복확인
            System.out.println(nickname+"을 확인하세요");
            Map<String, Object> validResult = checkNicknameValid(nickname);
            if(validResult.get("result").equals("NICKNAME_VALID"))
            System.out.println(validResult+"결과입니다");
            dao.updateNickname(memberId, nickname);
            System.out.println("닉네임업데이트를 실행합니다");
            nicknameLogDao.insert(new NicknameLog(memberId, nickname));
            System.out.println("닉네임이 업데이트 되었습니다.");
            result=1;
        }
        System.out.println(result+"업데이트 결과");
		return result;
	}

    @Override
    public ProfileFile uploadFile(MultipartFile file, String path) throws IOException {
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String completePath = path + File.separator + file.getOriginalFilename();

        InputStream fis = file.getInputStream();
        OutputStream fos = new FileOutputStream(completePath);

        byte[] buf = new byte[1024];
        int size = 0;
        while ((size = fis.read(buf)) > 0) {
            fos.write(buf, 0, size);
        }

        fos.close();
        fis.close();
        ProfileFile profileFile = new ProfileFile(file.getOriginalFilename());
        profileFileDao.insert(profileFile);
        return profileFile;
    }
}
