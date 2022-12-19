package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.meeting.MeetingThumbView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultMeetingService implements MeetingService{

    @Autowired
    private MeetingDao dao;

    public DefaultMeetingService() {
    }

    public DefaultMeetingService(MeetingDao dao){
        this.dao = dao;
    }

    @Override
    public List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed) {
        // 페이징옵션이 없을 때 ex:모임 리스트 조회 첫 화면
        MeetingThumbnailPagination pageable = new MeetingThumbnailPagination(startId, keyword, isClosed);
        List<MeetingThumbView> meetingViews = dao.getThumbList(pageable);

        // 응답에 맞게 데이터 변환
        List<MeetingThumbnailResponse> list = new ArrayList<>();
        for(MeetingThumbView m : meetingViews) {
            list.add(MeetingThumbnailResponse.of(m));
        }

        return list;
    }
}
