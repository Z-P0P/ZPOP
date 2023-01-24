package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import com.zpop.web.dto.admin.AdminCommentDto;

public interface AdminCommentService {

    List<AdminCommentDto> getList(int page, String keyword, String option, Date minDate, Integer period, Integer num, String order);
    int countBySearch(String keyword, String option, Date minDate, Integer period);
    int updateDeleteAt(List<Integer> ids, boolean isDeleted);
}
