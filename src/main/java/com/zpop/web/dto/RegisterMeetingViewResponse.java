package com.zpop.web.dto;

import java.util.List;

public class RegisterMeetingViewResponse {
	
	private List<RegionDto> regions;
	private List<CategoryDto> categories;
	private List<ContactTypeDto> contactTypes;
	private List<AgeRangeDto> ageRanges;
	
	public RegisterMeetingViewResponse(List<RegionDto> regions, List<CategoryDto> categories,
			List<ContactTypeDto> contactTypes, List<AgeRangeDto> ageRanges) {
		this.regions = regions;
		this.categories = categories;
		this.contactTypes = contactTypes;
		this.ageRanges = ageRanges;
	}
	
	public List<RegionDto> getRegions() {
		return regions;
	}
	public void setRegions(List<RegionDto> regions) {
		this.regions = regions;
	}
	public List<CategoryDto> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
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

}
