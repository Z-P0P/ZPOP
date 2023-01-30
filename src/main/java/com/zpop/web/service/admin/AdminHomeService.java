package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.dto.admin.OptionCountDto;

public interface AdminHomeService {
    
    List<OptionCountDto> countMeetingByContactType();
    List<OptionCountDto> countMeetingByAgeRange();
    List<OptionCountDto> countMeetingByGenderCategory();
    List<OptionCountDto> countMeetingByClosedAndNotClosed();
    int countMemberByNotResigned();
}
