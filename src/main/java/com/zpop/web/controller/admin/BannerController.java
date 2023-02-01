package com.zpop.web.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.entity.Banner;
import com.zpop.web.service.admin.AdminBannerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController("adminBannerController")
@RequestMapping("/api/admin/banner")
public class BannerController {
    
    @Autowired 
    AdminBannerService service;

    @GetMapping("list")
    public List<Banner> getList(
        @RequestParam(name="page", defaultValue="1") int page, 
        @RequestParam(name="size", defaultValue="10") int size, 
        @RequestParam(name="order", defaultValue="desc") String order){

        return service.getList(page, size, order);
    }

    @PostMapping()
    public int insert(@RequestPart Banner banner, @RequestPart MultipartFile bannerImg, HttpServletRequest request) throws IOException{

        String realPath = request.getServletContext().getRealPath("/image/banner");
        int result = service.insert(banner,bannerImg,realPath);
        
        return result;
    }
}
