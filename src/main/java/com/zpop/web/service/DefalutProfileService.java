package com.zpop.web.service;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefalutProfileService implements ProfileService{

    private final MemberDao dao;

    @Autowired
    public DefalutProfileService(MemberDao dao) {
        this.dao = dao;
    }

    @Override
    public Member getById(int id) {


        return dao.getById(id);
    }


}
