package com.zpop.web.service;

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
	
	@Autowired
	public RegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
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
	
	public boolean checkNicknameValid(String nickname) {
		if (nickname.isBlank() || nickname.isEmpty() || nickname.length() > MAX_NICKNAME_LENGTH) {
			return false;
		} 
		else {
			return true;
		}
	}
	
	public boolean checkNicknameRegisted (String nickname) {
		Member member = memberDao.getByNickname(nickname);
		return (member==null);
	}
	
	
	
}
