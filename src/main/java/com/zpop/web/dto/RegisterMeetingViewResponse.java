package com.zpop.web.dto;

import java.util.List;

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
public class RegisterMeetingViewResponse {
	
	private List<RegionDto> regions;
	private List<CategoryDto> categories;
	private List<ContactTypeDto> contactTypes;
	private List<AgeRangeDto> ageRanges;
}
