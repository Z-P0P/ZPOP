package com.zpop.web.service;

import com.zpop.web.entity.Meeting;
import com.zpop.web.view.MeetingThumbView;

import java.util.List;

public interface MeetingService {
    List<MeetingThumbView> getList();
}
