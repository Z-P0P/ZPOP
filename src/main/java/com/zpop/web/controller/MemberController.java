package com.zpop.web.controller;


import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.service.MeetingService;
import com.zpop.web.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*****
 * 1. 다른 사용자 프로필 조회              --> 모달 url 불필요
 * 2. 마이 프로필 페이지 진입 (/member/me) --> 페이지
 * 3. /member/me/meeting  --> 내가 참여한 모임 (임시)
 * 4. /member/me/gather   --> 내가 모집한 모임 (임시)
 * 5. 탈퇴하기 url ?
 * *****/

// 1. 헤더에서 마이프로필 클릭 -> 로그인한 유저가 권한이 있는지 확인
// 2. 로그인한 유저가 권한이 있다면 , 마이프로필 페이지로 이동
// 2-2. 권한이 없다면 접근권한이 없습니다! 띄우기
// 3. 마이프로필 페이지로 이동

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService service;

    @Autowired
    private MeetingService meetingService;

    //다른 사용자 프로필 조회 (return 경로 임시)
    @GetMapping("{id}")
    public String getMember(@PathVariable ("id") int id, Model model){
        Member member = service.getById(id);
        model.addAttribute("member", member);
        return "member/my-profile";

    }

    //마이페이지 진입
    @GetMapping("/me")
        //user id는 세션에서 받아와야함
    public String getMyPage(HttpSession session , Model model) {
        Member member = (Member) session.getAttribute("member");

        return "member/my-profile";
        //member 없을때 처리
        //만약 로그인한 사람이 아닌데 들어올 경우 -> 처리 (멤버가져오기)
    }
    @GetMapping("me/edit")
    public String getEditPage(HttpSession session, Model model){
        Member member = (Member) session.getAttribute("member");
        return "member/profile-edit";
    }

    @GetMapping("/me/meeting")
    public String getMeeting(HttpSession session , Model model) {
        Member member = (Member) session.getAttribute("member");
        List<MyMeetingResponse> meetings = service.getMyMeeting(member.getId());

        model.addAttribute("meetings", meetings);

        return "member/my-meeting";
    }
    // 권한 확인



    @GetMapping("/me/gathering")
    public String getGathering(HttpSession session, Model model){
        Member member = (Member) session.getAttribute("member");
        List<MyMeetingResponse> meetings = service.getMyGathering(member.getId());
        model.addAttribute("meetings", meetings);


        return "member/my-gathering";

    }
//@RequestBody HashMap<String, Object> param

    @ResponseBody
    @GetMapping("/eval/{dataId}")
    public List<EvalMemberDto> getParticipants(@PathVariable("dataId") int meetingId)
    {

         List<EvalMemberDto> participant = service.getEvalMember(meetingId) ;
      
//        service.getDto( title, service.getParticipant(meetingId));
//        service.getParticipantNickname(meetingId);

        // Gson gson = new Gson();
        // String jsonString = gson.toJson(participant);
        // System.out.println(jsonString);
        // //데이터 , 성공여부 , status code , 에러에 해당하는 경우
        // System.out.println(meetingId);
        return participant;
    }
}
