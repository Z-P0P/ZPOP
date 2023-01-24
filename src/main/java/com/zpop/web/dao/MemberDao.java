package com.zpop.web.dao;

import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.MemberEval;
import com.zpop.web.entity.member.MyMeetingView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MemberDao {
	List<Member> getList(int size, int offset, String keyword, String option);

	Member getById(int id);

	Member getBySocialId(String socialId);

	Member getByNickname(String nickname);

	Member get(int id);

	int insert(Member member);

	int update(Member member);
	int countBySearch(String keyword, String option, Integer period, Date minDate);
	int count(int socialTypeId);


	List<Member> getListBySearch(int size, int offset, String keyword, String option, Integer period, Date minDate, String order);
	List<MyMeetingView> getMyMeeting(int memberId);
	List<MyMeetingView> getMyGathering(int memberId);

	List<EvalMemberDto> getEvalMember(int meetingId);
	int updateFameAll(List<MemberEval> evals);

    int updateAllIsSuspended(List<Integer> ids, Boolean isSuspended);
	
	int updateNickname(int memberId, String nickname);
}