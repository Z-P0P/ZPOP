package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.BannerDto;
import com.zpop.web.service.BannerService;


@RestController
@RequestMapping("/api/banner")
public class BannerController {
    
    @Autowired
    BannerService service;

    @GetMapping()
    public List<BannerDto> getActiveList() {
        return service.getAciveList();
    }
    
}
