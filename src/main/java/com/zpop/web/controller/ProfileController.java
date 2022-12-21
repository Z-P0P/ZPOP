package com.zpop.web.controller;

import com.zpop.web.entity.Member;
import com.zpop.web.entity.meeting.MeetingThumbView;
import com.zpop.web.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    //1. 헤더에서 마이프로필 클릭 -> 로그인한 유저가 권한이 있는지 확인
    //2. 로그인한 유저가 권한이 있다면 , 마이프로필 페이지로 이동
    //2-2. 권한이 없다면 접근권한이 없습니다! 띄우기


    //3. 마이프로필 페이지로 이동

    @GetMapping("/my-profile/{id}")
    public String getProfile(@PathVariable int id, Model model) {
        Member member = profileService.getById(id);
        //
        model.addAttribute("member" , member);

        return"profile/my-profile";
    }

    @GetMapping("/my-meeting/{id}")
    public String getMyMeeting(@PathVariable int id, Model model){
        List<MeetingThumbView> me = profileService.getMyMeeting(7);
        model.addAttribute("me", me);
        return "profile/my-meeting";
    }
    //권한 확인


}
