package com.zpop.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ZpopUserDetails implements UserDetails {

    private int id;
    private String password;
    private String name;
    private String profileImagePath;
    private int fame;


    private List<GrantedAuthority> authorities;
    private boolean enalbed;

    public ZpopUserDetails(int id, String password, String name, List<GrantedAuthority> authorities, boolean enalbed, String profileImagePath, int fame) {
        this.id = id;
        this.password = password;
        this.profileImagePath = profileImagePath;
        this.name = name;
        this.fame = fame;
        this.authorities = authorities;
        this.enalbed = enalbed;
    }

    public int getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enalbed;
    }
    
    public String getProfileImagePath() {
    	return this.profileImagePath;
    }
    
    public int getFame() {
        return fame;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    @Override
    public String toString() {
        return "ZpopUserDetails{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", authorities=" + authorities +
                ", enalbed=" + enalbed +
                '}';
    }
}
