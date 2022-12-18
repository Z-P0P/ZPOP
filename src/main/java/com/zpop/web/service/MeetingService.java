package com.zpop.web.service;

import com.zpop.web.dto.MeetingThumbResponse;

import java.util.List;

public interface MeetingService {
    List<MeetingThumbResponse> getList(int startId, String keyword, Boolean isClosed);
}
