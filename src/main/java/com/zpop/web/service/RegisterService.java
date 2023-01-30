package com.zpop.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.NicknameLogDao;
import com.zpop.web.dao.SocialTypeDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.NicknameLog;
import com.zpop.web.entity.SocialType;
import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;

@Service
public class RegisterService {
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private SocialTypeDao socialTypeDao;
	@Autowired
	private NicknameLogDao nicknameLogDao;
	
	private final int MAX_NICKNAME_LENGTH = 10;
	private final String nicknamePattern = "^(?=.*[a-zA-Z0-9가-힣])[a-zA-Z0-9가-힣]{0,10}$";


	@Transactional
	public Member registerMember(String nickname, String socialId, String LoginType) {
		
		Member registeredMember = memberDao.getBySocialId(socialId);
		if (registeredMember != null){
			throw new CustomException(ExceptionReason.SOCIAL_ID_ALREADY_REGISTERED);
		}

		SocialType socialType = socialTypeDao.get(LoginType);
		int socialTypeId = socialType.getId();


		Member member = Member.builder()
							.nickname(nickname)
							.socialId(socialId)
							.socialTypeId(socialTypeId)
							.build();

		memberDao.insert(member);

		NicknameLog log = NicknameLog.builder()
									.memberId(member.getId())
									.nickname(nickname)
									.build();

		nicknameLogDao.insert(log);

		return member;
	}
	
	public Map<String, Object> checkNicknameValid(String nickname) {
		Map<String, Object> result = new HashMap<>();
		boolean isRegexMatch = Pattern.matches(nicknamePattern, nickname);
		
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
