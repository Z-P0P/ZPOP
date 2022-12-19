package com.zpop.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;


@Service
public class NaverLoginService implements LoginService{
	
	private MemberDao memberDao;
	private final String TOKEN_DOMAIN_URL="https://nid.naver.com";
	private final String URI = "/oauth2.0/token";
	private final String GRANT_TYPE = "authorization_code";
	private final String CLIENT_ID;
	private final String CLIENT_SECRET;
	private final String PROFILE_DOMAIN_URL="https://openapi.naver.com/";
	private final String PROFILE_URI = "v1/nid/me";
	private final String TOKEN_TYPE= "Bearer ";
	
	@Autowired
	public NaverLoginService(MemberDao memberDao
			, @Value("${NAVER_CLIENT_ID}") String CLIENT_ID
			, @Value("${NAVER_CLIENT_SECRET}") String CLIENT_SECRET) {
		this.memberDao = memberDao;
		this.CLIENT_ID = CLIENT_ID;
		this.CLIENT_SECRET = CLIENT_SECRET;
	}
	
	public String getAccessToken(String code, String state) throws IOException, InterruptedException {

		/* WebClient 방식 Spring Reactive Web 의존성 */
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	    parameters.set("grant_type", GRANT_TYPE);
	    parameters.set("client_id", CLIENT_ID);
	    parameters.set("client_secret", CLIENT_SECRET);
	    parameters.set("code", code);		
	    parameters.set("state", state);

		WebClient client = WebClient.builder()
				.baseUrl(TOKEN_DOMAIN_URL)
				.build();
		
		TokenResponse response = client.post()
				.uri(URI)
				.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.body(BodyInserters.fromValue(parameters))
				.retrieve()
				.bodyToMono(TokenResponse.class)
				.block();
		
		if (response.getError() != null) {
			System.out.println("토큰 요청 중 에러가 발생하였음");
		}
		
		return response.getAccessToken();
	}

	@Override
	public String getSocialId(String accessToken) {
		
		WebClient client = WebClient.builder()
				.baseUrl(PROFILE_DOMAIN_URL)
				.build();
		
		NaverProfileResponse response = client.get()
				.uri(PROFILE_URI)
				.header("Authorization", TOKEN_TYPE + accessToken)
				.retrieve()
				.bodyToMono(NaverProfileResponse.class)
				.block();
		
		return response.getResponse().getId();
	}

	@Override
	public Member getMemberInfo(String socialId) {
		
		return memberDao.getBySocialId(socialId);
	}
	
	
	
}
