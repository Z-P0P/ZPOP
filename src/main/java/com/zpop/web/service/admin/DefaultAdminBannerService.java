package com.zpop.web.service.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.dao.BannerDao;
import com.zpop.web.entity.Banner;
import com.zpop.web.utils.FileNameGenerator;

@Service
public class DefaultAdminBannerService implements AdminBannerService{

    @Autowired
    BannerDao dao;

    @Override
    public List<Banner> getList(int page, int size, String order) {
        int offset = (size) * (page - 1);
        return dao.getList(offset, size, order);
    }

    @Override
    public int removeAll(List<Integer> ids) {
        return dao.removeAll(ids);
    }

    @Override
    public int update(Banner banner) {
        return dao.update(banner);
    }

    @Override
    public int insert(Banner banner, MultipartFile bannerImg, String realPath) throws IOException{
        
        File pathFile = new File(realPath);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
        
		String extension = StringUtils.getFilenameExtension(bannerImg.getOriginalFilename());
        
        FileNameGenerator fileNameGenerator = new FileNameGenerator("ZPOP_BANNER",extension);
        String fileName = fileNameGenerator.getFileNameWithDateTime();

		String completePath = realPath + File.separator + fileName;

		InputStream fis = bannerImg.getInputStream();
		OutputStream fos = new FileOutputStream(completePath);

		byte[] buf = new byte[1024];
		int size = 0;
		while ((size = fis.read(buf)) > 0) {
			fos.write(buf, 0, size);
		}
        
		fos.close();
		fis.close();
        
        banner.setImagePath(fileName);

        return dao.insert(banner);

    }
}
