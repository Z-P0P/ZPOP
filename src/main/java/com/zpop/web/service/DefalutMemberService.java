package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * 작성자: 임형미
 */
@Service
public class DefalutMemberService implements MemberService{

    private final MemberDao dao;
    private final MeetingDao mtDao;

    //mtDao가 아닌 view를 이용한 dao로 수정 필요함
    @Autowired
    public DefalutMemberService(MemberDao dao, MeetingDao mtDao) {
        this.dao = dao;
        this.mtDao = mtDao;
    }

    @Override
    public Member getById(int id) {
        return dao.getById(id);
    }

    //서비스 구현 = 다오연결
    //getMyMeeting 아직 미완성 -> meetingdaoMapper xml 파일에서 
    //수정필요
    @Override
    public List<MeetingThumbnailView> getMyMeeting(int id) {
       List<MeetingThumbnailView> result = mtDao.getMeetingList(7);
        return result;

    }


}
