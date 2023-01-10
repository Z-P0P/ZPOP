package com.zpop.web.dto.admin;

import java.util.List;

import com.zpop.web.dto.AgeRangeDto;
import com.zpop.web.dto.CategoryDto;
import com.zpop.web.dto.ContactTypeDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.ParticipantDto;
import com.zpop.web.dto.RegionDto;

public class AdminMeetingDetailsResponse {
	private AdminMeetingDetailsDto dto;
	private List<CategoryDto> categories;
	private List<RegionDto> regions;
	private List<ContactTypeDto> contactTypes;
	private List<AgeRangeDto> ageRanges;
	private List<MeetingParticipantsDto> participants;
	
	public AdminMeetingDetailsResponse(AdminMeetingDetailsDto dto, 
			List<CategoryDto> categories, List<RegionDto> regions,
			List<ContactTypeDto> contactTypes, List<AgeRangeDto> ageRanges, 
			List<MeetingParticipantsDto> participants) {
		this.dto = dto;
		this.categories = categories;
		this.regions = regions;
		this.contactTypes = contactTypes;
		this.ageRanges = ageRanges;
		this.participants = participants;
	}

	public AdminMeetingDetailsDto getDto() {
		return dto;
	}

	public void setDto(AdminMeetingDetailsDto dto) {
		this.dto = dto;
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}

	public List<RegionDto> getRegions() {
		return regions;
	}

	public void setRegions(List<RegionDto> regions) {
		this.regions = regions;
	}

	public List<ContactTypeDto> getContactTypes() {
		return contactTypes;
	}

	public void setContactTypes(List<ContactTypeDto> contactTypes) {
		this.contactTypes = contactTypes;
	}

	public List<AgeRangeDto> getAgeRanges() {
		return ageRanges;
	}

	public void setAgeRanges(List<AgeRangeDto> ageRanges) {
		this.ageRanges = ageRanges;
	}

	public List<MeetingParticipantsDto> getParticipants() {
		return participants;
	}

	public void setParticipants(List<MeetingParticipantsDto> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "AdminMeetingDetailsResponse [dto=" + dto + ", categories=" + categories + ", regions=" + regions
				+ ", contactTypes=" + contactTypes + ", ageRanges=" + ageRanges + ", participants=" + participants
				+ "]";
	}
	
}
