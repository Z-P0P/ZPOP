package com.zpop.web.security;

import com.zpop.web.dao.AdminDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Admin;
import com.zpop.web.entity.Member;
import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberDao.getByNickname(username);
        if(member == null)
            throw new CustomException(ExceptionReason.AUTHENTICATION_ERROR);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));

        return new ZpopUserDetails(member.getId(), null, member.getNickname(), authorities, true, member.getProfileImagePath(), member.getFame());
    }

    public UserDetails loadWithAdmin(String username, String pwd) throws UsernameNotFoundException {

        Admin admin = adminDao.getByName(username);

        if(admin == null ||
                !passwordEncoder.matches(pwd, admin.getPwd())
        )
            throw new CustomException(ExceptionReason.AUTHENTICATION_ERROR);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));
        authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRole()));

        return new ZpopUserDetails(admin.getId(), null, admin.getName(), authorities, true, null , null);
    }

    public void createAdmin(String name, String pwd) {
        Admin admin = Admin.builder()
                .name(name)
                .pwd(passwordEncoder.encode(pwd))
                .createdAt(new Date())
                .build();

        adminDao.insert(admin);
    }
}
