package com.zpop.web.dto.admin;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegionDto {
	
	private int id;
	private String name;
	private int num;
	private Date deletedAt;
	
}
