package com.zpop.web.dto.admin;

import java.util.List;

import com.zpop.web.dto.RegisterMeetingViewResponse;
import com.zpop.web.entity.participation.ParticipationInfoView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMeetingDetailsResponse {
	private AdminMeetingDetailsDto details;
	RegisterMeetingViewResponse options;
	private List<ParticipationInfoView> participants;
	
}
