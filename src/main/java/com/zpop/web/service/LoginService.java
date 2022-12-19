package com.zpop.web.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zpop.web.entity.Member;


public interface LoginService {

	String getAccessToken(String code, String state) throws IOException, InterruptedException;

	String getSocialId(String accessToken);

	Member getMemberInfo(String socialId);

}
