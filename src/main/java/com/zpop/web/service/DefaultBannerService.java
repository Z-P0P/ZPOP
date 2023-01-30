package com.zpop.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.BannerDao;
import com.zpop.web.dto.BannerDto;

@Service
public class DefaultBannerService implements BannerService{

    @Autowired
    BannerDao dao;

    @Override
    public List<BannerDto> getAciveList() {
        return dao.getActiveList();
    }
    
}
