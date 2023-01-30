package com.zpop.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.ognl.ObjectArrayPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.admin.OptionCountDto;
import com.zpop.web.service.admin.AdminHomeService;

@RestController("adminHomeController")
@RequestMapping("/api/admin/home")
public class HomeController {
    
    @Autowired
    AdminHomeService service;

    @GetMapping()
    public Map<String, Object> getDashboard(){
        Map<String, Object> result = new HashMap<>();

        List<OptionCountDto> ageRangeCount = service.countMeetingByAgeRange();
        List<OptionCountDto> contactTypeCount = service.countMeetingByContactType();
        List<OptionCountDto> genderCategoryCount = service.countMeetingByGenderCategory();
        List<OptionCountDto> closedNotClosedCount = service.countMeetingByClosedAndNotClosed();

        int notResignedMemberNum = service.countMemberByNotResigned();

        result.put("ageRange", ageRangeCount);
        result.put("contactType", contactTypeCount);
        result.put("genderCategory", genderCategoryCount);
        result.put("closedNotClosed", closedNotClosedCount);
        result.put("memberNum", notResignedMemberNum);

        
       return result;
    }
}
