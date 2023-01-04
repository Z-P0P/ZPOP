package com.zpop.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.SocialTypeDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.SocialType;

@Service
public class RegisterService {
	
	private MemberDao memberDao;
	private SocialTypeDao socialTypeDao;
	
	private final int MAX_NICKNAME_LENGTH = 10;
	private final String nicknamePattern = "^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$";
	
	@Autowired
	public RegisterService(MemberDao memberDao, SocialTypeDao socialTypeDao) {
		this.memberDao = memberDao;
		this.socialTypeDao = socialTypeDao;
	}
	
	public Member registerMember(String nickname, String socialId, String LoginType) {
		

		SocialType socialType = socialTypeDao.get(LoginType);
		int socialTypeId = socialType.getId();
	
		Member member = new Member();
		member.setNickname(nickname);
		member.setSocialId(socialId);
		member.setSocialTypeId(socialTypeId);
		memberDao.insert(member);

		return memberDao.getBySocialId(socialId);
	}
	
	public Map<String, Object> checkNicknameValid(String nickname) {
		Map<String, Object> result = new HashMap<>();
		boolean isRegexMatch = Pattern.matches(nicknamePattern, nickname);
		System.out.println(isRegexMatch);
		if (nickname.isBlank() || nickname.isEmpty() || 
				nickname.length() > MAX_NICKNAME_LENGTH
				|| !isRegexMatch) {
			result.put("result", "FORMAT_NOT_ALLOWED");
			return result;
		} 
		
		Member member = memberDao.getByNickname(nickname);
		if (member != null) {
			result.put("result", "NICKNAME_ALREADY_USED");
			return result;
		}
		
		result.put("result", "NICKNAME_VALID");
		return result;
	}
}
