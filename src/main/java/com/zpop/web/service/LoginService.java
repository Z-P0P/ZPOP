package com.zpop.web.service;

import java.io.IOException;

import com.zpop.web.dto.BlockedMemberDto;
import com.zpop.web.entity.Member;


public interface LoginService {

	String getAccessToken(String code, String state) throws IOException, InterruptedException;

	String getSocialId(String accessToken);

	Member getMemberInfo(String socialId);

	BlockedMemberDto getBlockedMemberById(int memberId);
}
