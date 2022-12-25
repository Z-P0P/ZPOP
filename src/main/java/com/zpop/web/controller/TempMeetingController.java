package com.zpop.web.controller;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/meeting")
public class TempMeetingController {

    @Autowired
    private MeetingService service;

    public TempMeetingController() {
    }

    public TempMeetingController(MeetingService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getListView(
            Model model
            ){
        List<MeetingThumbnailResponse> meetings = service.getList();

        model.addAttribute("meetings", meetings);

        return "meeting/list";
    }

    @GetMapping("/api/list")
    @ResponseBody
    public List<MeetingThumbnailResponse> getList(
            @RequestParam(required = false, defaultValue = "0") int start,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) String regions,
            @RequestParam(required = false) Boolean isClosed
    ){  
        List<MeetingThumbnailResponse> meetings = service.getList(start, keyword, category, regions, isClosed);

        return meetings;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public boolean delete(
            @PathVariable(name = "id") int id
    ) {
        Member testMember = new Member();
        boolean result = service.delete(id, testMember);
        return true;
    }
}
