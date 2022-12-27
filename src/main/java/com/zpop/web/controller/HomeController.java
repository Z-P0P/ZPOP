package com.zpop.web.controller;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MeetingService meetingService;

    public HomeController() {
    }

    public HomeController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @RequestMapping("/")
    public String root() {
        return "forward:/meeting/list";
    }

    @GetMapping("/search/{keyword}")
    public String searchView(Model model, @PathVariable String keyword) {
        List<MeetingThumbnailResponse> meetings = meetingService.getList(keyword);

        model.addAttribute("meetings", meetings);

        return "meeting/search";
    }
    
}
