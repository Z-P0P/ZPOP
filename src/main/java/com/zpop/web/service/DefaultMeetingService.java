package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.entity.Meeting;
import com.zpop.web.view.MeetingThumbView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<MeetingThumbView> getList() {
        List<MeetingThumbView> meetings = dao.getThumbList();
        for (MeetingThumbView m: meetings)
            System.out.println(m.toString());
        return meetings;
    }
}
