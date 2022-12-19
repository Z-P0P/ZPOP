package com.zpop.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;

@Service
public class KakaoLoginService implements LoginService{
	
	private MemberDao memberDao;
	
	@Autowired
	public KakaoLoginService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	
	public String getAccessToken(String code, String state) throws IOException, InterruptedException {

		String domainUrl="https://kauth.kakao.com";
		String uri = "/oauth/token";
		String grantType = "authorization_code";
		String clientId = "6caab81a61e4a30d34d021c1a41e8322";
		String redirectUri = "http://localhost:8080/login/oauth/kakao";
		
		/* WebClient 방식 Spring Reactive Web 의존성 */
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	    parameters.set("grant_type", grantType);
	    parameters.set("client_id", clientId);
	    parameters.set("redirect_uri", redirectUri);
	    parameters.set("code", code);		

		WebClient client = WebClient.builder()
				.baseUrl(domainUrl)
				.build();
		
		TokenResponse response = client.post()
				.uri(uri)
				.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.body(BodyInserters.fromValue(parameters))
				.retrieve()
				.bodyToMono(TokenResponse.class)
				.block();
		
		return response.getAccess_token();
		
		
		/* RestTemplate 방식 Spring Web 의존성
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	    parameters.set("grant_type", grantType);
	    parameters.set("client_id", clientId);
	    parameters.set("redirect_uri", redirectUri);
	    parameters.set("code", code);

	    HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(parameters, headers);
	    ResponseEntity<TokenResponse> apiResponse = restTemplate.postForEntity(requestUrl, restRequest, TokenResponse.class);
	    TokenResponse responseBody = apiResponse.getBody();
	    System.out.println(responseBody.getAccess_token());

	    return null;
	    */
		
		
		/* HttpClient 를 사용하는 방식 (Spring 의존성 X)
		Map<Object, Object> data = new HashMap<>();
		data.put("grant_type", grantType);
		data.put("client_id", clientId);
		data.put("redirect_url", redirectUri);
		data.put("code", code);
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(requestUrl))
				.header("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
				.POST(buildFormDataFromMap(data))
				.build();
		
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		ObjectMapper mapper = new ObjectMapper();
		TokenResponse responseObject = mapper.readerFor(TokenResponse.class).readValue(response.body());
		return responseObject.getAccess_token();
		
		private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
		*/
		
	}

	@Override
	public String getSocialId(String accessToken) {
		String domainUrl="https://kapi.kakao.com/";
		String uri = "v2/user/me";
		String tokenType = "Bearer ";
		
		
		WebClient client = WebClient.builder()
				.baseUrl(domainUrl)
				.build();
		
		KakaoProfileResponse response = client.get()
				.uri(uri)
				.header("Authorization", tokenType + accessToken)
				.retrieve()
				.bodyToMono(KakaoProfileResponse.class)
				.block();
		
		System.out.println(response.getId());

		
		return response.getId();
	}

	@Override
	public Member getUserInfo(String socialId) {
		
		return memberDao.getBySocialId(socialId);
	
	}
	
	
	
}
