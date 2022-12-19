package com.zpop.web.controller;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
            Model model,
            @RequestParam(required = false, defaultValue = "0") int startId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isClosed
            ){
        List<MeetingThumbnailResponse> meetings = service.getList(startId, keyword, isClosed);

        model.addAttribute("meetings", meetings);

        return "meeting/list";
    }

    @GetMapping("/api/list")
    @ResponseBody
    public List<MeetingThumbnailResponse> getList(
            @RequestParam(required = false, defaultValue = "0") int startId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isClosed
    ){
        List<MeetingThumbnailResponse> meetings = service.getList(startId, keyword, isClosed);

        return meetings;
    }
}
