package com.zpop.web.service.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.entity.Banner;


public interface AdminBannerService {

    List<Banner> getList(int page, int size, String order);
    int removeAll(List<Integer> ids);
    int update(Banner banner);
    int insert(Banner banner, MultipartFile bannerImg, String realPath) throws IOException;
}
