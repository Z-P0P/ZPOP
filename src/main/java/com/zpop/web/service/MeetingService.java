package com.zpop.web.service;

import com.zpop.web.dto.MeetingThumbnailResponse;

import java.util.List;

public interface MeetingService {
    List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed);
}
