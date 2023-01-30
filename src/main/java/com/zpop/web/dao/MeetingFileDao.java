package com.zpop.web.dao;

import com.zpop.web.entity.MeetingFile;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.member.MyMeetingView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingFileDao {
	List<MeetingFile> getList(int meetingId);
	int insertAll(List<MeetingFile> images);
	int deleteAll(List<MeetingFile> prevAttachedImages);
	int insert(MeetingFile meetingFile);
	int updateAllMeetingId(List<MeetingFile> images);
	int deleteAllExcept(List<MeetingFile> images);	
}