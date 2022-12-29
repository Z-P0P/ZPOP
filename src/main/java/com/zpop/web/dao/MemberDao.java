package com.zpop.web.dao;

import com.zpop.web.entity.Member;
import com.zpop.web.entity.member.MyMeetingView;
import org.apache.ibatis.annotations.Mapper;

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
	int countBySearch(String keyword, String option);
	int count(int socialTypeId);



	List<Member> getListBySearch(int size, int offset, String keyword, String option);
	List<MyMeetingView> getMeeting(int meetingId);
	List<MyMeetingView> getMyMeeting(int memberId);
	List<MyMeetingView> getMyGathering(int memberId);

}