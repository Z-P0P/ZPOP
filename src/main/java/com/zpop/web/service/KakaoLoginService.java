package com.zpop.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.NotificationDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.entity.Member;

@Service
public class KakaoLoginService implements LoginService{
	
	private MemberDao memberDao;
	
	private final String DOMAIN_URL="https://kauth.kakao.com";
	private final String URI = "/oauth/token";
	private final String GRANT_TYPE = "authorization_code";
	private final String CLIENT_ID;
	private final String DIRECT_URI; 
	private final String PROFILE_DOMAIN_URL="https://kapi.kakao.com/";
	private final String PROFILE_URI = "v2/user/me";
	
	@Autowired
	public KakaoLoginService(MemberDao memberDao,
			@Value("${KAKAO_CLIENT_ID}") String CLIENT_ID,
			@Value("${KAKAO_REDIRECT_URI}") String REDIRECT_URI) {
		this.memberDao = memberDao;
		this.CLIENT_ID = CLIENT_ID;
		this.DIRECT_URI = REDIRECT_URI;
	}
	
	@Autowired
	private NotificationDao notificationDao;
	
	@Autowired
	private ParticipationDao participationDao;
	
	
	public String getAccessToken(String code, String state) throws IOException, InterruptedException {

		
		/* WebClient 방식 Spring Reactive Web 의존성 */
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	    parameters.set("grant_type", GRANT_TYPE);
	    parameters.set("client_id", CLIENT_ID);
	    parameters.set("redirect_uri", DIRECT_URI);
	    parameters.set("code", code);		

		WebClient client = WebClient.builder()
				.baseUrl(DOMAIN_URL)
				.build();
		
		TokenResponse response = client.post()
				.uri(URI)
				.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.body(BodyInserters.fromValue(parameters))
				.retrieve()
				.bodyToMono(TokenResponse.class)
				.block();
		
		return response.getAccessToken();
	}

	@Override
	public String getSocialId(String accessToken) {
		
		// 띄어쓰기 주의
		String tokenType = "Bearer ";
		
		
		WebClient client = WebClient.builder()
				.baseUrl(PROFILE_DOMAIN_URL)
				.build();
		
		//GET,POST 요청 둘다 가능하지만, 편의상 GET으로 요청함
		KakaoProfileResponse response = client.get()
				.uri(PROFILE_URI)
				.header("Authorization", tokenType + accessToken)
				.retrieve()
				.bodyToMono(KakaoProfileResponse.class)
				.block();
		

		
		return response.getId();
	}

	@Override
	public Member getMemberInfo(String socialId) {
		
		Member member = memberDao.getBySocialId(socialId);
		int participantId = member.getId();
		int[] participantIds = participationDao.getListByParticipantId(participantId);
		
		if(participantIds[0] != 0)
			createNotification(participantIds[0],"meeting/evaluation",1);
		
		return memberDao.getBySocialId(socialId);
	
	}
	
	private void createNotification(int memberId, String url, int type) {
		notificationDao.insertCommentNotification(memberId,url,type);
	}
	
	
}
