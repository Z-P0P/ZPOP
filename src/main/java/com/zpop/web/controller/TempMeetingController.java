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

    @DeleteMapping("/{id}/participant/{participantId}")
    @ResponseBody
    public boolean kick(
            @PathVariable(name = "id") int id,
            @PathVariable(name = "participantId") int participantId
    ) {
        Member testMember = new Member();
        testMember.setId(3);
        boolean result = service.kick(id, participantId, testMember);
        return result;
    }

    @PatchMapping("/close/{id}")
    @ResponseBody
    public boolean close (
        @PathVariable(name = "id") int id
    ) {
        Member testMember = new Member();
        return service.close(id, testMember);
    }
}
