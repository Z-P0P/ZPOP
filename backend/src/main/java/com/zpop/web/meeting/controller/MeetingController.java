package com.zpop.web.meeting.controller;

import com.zpop.web.meeting.dto.MeetingResponse;
import com.zpop.web.meeting.service.MeetingService;

import com.zpop.web.security.ZpopUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService service;

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponse> getDetail(
            @PathVariable int id,
            @AuthenticationPrincipal ZpopUserDetails userDetails
    ){
        Integer memberId = null;

        if(userDetails != null)
            memberId = userDetails.getId();

        MeetingResponse response = service.getDetail(id, memberId);

        return ResponseEntity.ok().body(response);
    }
}
