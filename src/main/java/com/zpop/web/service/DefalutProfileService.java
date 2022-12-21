package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.meeting.MeetingThumbView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefalutProfileService implements ProfileService{

    private final MemberDao dao;
    private final MeetingDao mtDao;

    @Autowired
    public DefalutProfileService(MemberDao dao, MeetingDao mtDao) {
        this.dao = dao;
        this.mtDao = mtDao;
    }

    @Override
    public Member getById(int id) {


        return dao.getById(id);
    }

    //서비스 구현 = 다오연결
    @Override
    public List<MeetingThumbView> getMyMeeting(int id) {
       List<MeetingThumbView> result = mtDao.getMeetingList(7);
        return result;

    }


}
