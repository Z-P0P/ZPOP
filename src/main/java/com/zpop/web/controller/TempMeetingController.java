package com.zpop.web.controller;

import com.zpop.web.service.MeetingService;
import com.zpop.web.view.MeetingThumbView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getListView(Model model){
        List<MeetingThumbView> meetings = service.getList();
        model.addAttribute("meetings", meetings);
        return "meeting/list";
    }
}
