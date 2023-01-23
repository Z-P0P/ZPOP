package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.dto.admin.AdminCommentDto;

@Service
public class DefaultAdminCommentService implements AdminCommentService{

    @Autowired
    CommentDao dao;

    @Override
    public List<AdminCommentDto> getList(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order){
        int size = num;
        int offset=(page-1)*size;
        List<AdminCommentDto> result = dao.getAdminList(offset, size, keyword,option,minDate,period,order);

        return result;
    }
    
    public int countBySearch(String keyword, String option, Date minDate, Integer period){
        return dao.countBySearch(keyword, option, minDate, period);
    }

    @Override
    public int updateDeleteAt(List<Integer> ids, boolean isDeleted) {
        int result = 0;
        if (isDeleted){
            result = dao.updateAllDeletedAt(ids);
        }
        else{
            result = dao.removeAllDeletedAt(ids);
        }
        return result;
    }
}
