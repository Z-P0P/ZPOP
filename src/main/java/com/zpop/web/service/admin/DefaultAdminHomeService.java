package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dto.admin.OptionCountDto;

@Service
public class DefaultAdminHomeService implements AdminHomeService{

    @Autowired
    MeetingDao meetingDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public List<OptionCountDto> countMeetingByContactType() {
        return meetingDao.countByActiveContactType();
    }

    @Override
    public List<OptionCountDto> countMeetingByAgeRange() {
        return meetingDao.countByActiveAgeRange();
    }

    @Override
    public List<OptionCountDto> countMeetingByGenderCategory() {
        List<OptionCountDto> result = meetingDao.countByGenderCategory();
        System.out.println(result);
        for (var dto : result){
            switch(dto.getId()){
                case 0:
                dto.setName("남녀 모두");
                break;
                case 1:
                dto.setName("남자만");
                break;
                case 2:
                dto.setName("여자만");
                break;
            }
        }
        return result;
    }

    @Override
    public List<OptionCountDto> countMeetingByClosedAndNotClosed() {
        return meetingDao.countClosedAndNotClosed();
    }

    @Override
    public int countMemberByNotResigned() {
        return memberDao.countByNotResigned();
    }
    
}
