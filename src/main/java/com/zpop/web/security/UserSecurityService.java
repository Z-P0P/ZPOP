package com.zpop.web.security;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberDao.getByNickname(username);
        if(member == null)
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));

        return new ZpopUserDetails(member.getId(), null, member.getNickname(), authorities, true, member.getProfileImagePath(), member.getFame());
    }

    public UserDetails loadUserByUsername(String username, boolean isAdmin) throws UsernameNotFoundException {
        //TODO: admin 구현
        return null;
    }
}
