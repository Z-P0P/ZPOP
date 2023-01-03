package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.participation.ParticipationInfoView;

@Mapper
public interface ParticipationDao {
	
    int insert(int meetingId, int memberId);
    
    Participation get(int id);
    
    List<Participation> getListByMeetingId(int meetingId);
    List<Participation> getList();
    List<MeetingParticipantsDto>getByMeetingId(int meetingId);
    List<AdminParticipationDto> getAdminViewList(int size, int offset, String keyword, String option);

    /**
     * 닉네임, 프로필 사진 등 참여자의 정보가 포함된 view 조회
     * @param meetingId
     * @return
     */
    List<ParticipationInfoView> getParticipantInfoByMeetingId(int meetingId);

    int[] getListByParticipantId(int participantId);

	int countBySearch(String keyword, String option);

	int countByMeetingId(int meetingId);

    int updateBannedAt(int id);

    int updateCanceledAt(int id);
}
