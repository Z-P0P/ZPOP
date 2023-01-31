package com.zpop.web.service;

import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.dto.ProfileResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.ProfileFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MemberService {

        Member getById(int id);
        List<MyMeetingResponse> getMyMeeting(int memberId);
        List<MyMeetingResponse> getMyGathering(int memberId);
        List<EvalMemberDto> getEvalMember(int meetingId);
        Map<String,Object> getRateData(EvalDto dto);
        ProfileResponse getProfile(int id);

        Map<String, Object> checkNicknameValid(String nickname);
        int updateNickname(int memberId, String nickname);

        ProfileFile uploadFile(MultipartFile file, String path, int memberId) throws IOException;
}

