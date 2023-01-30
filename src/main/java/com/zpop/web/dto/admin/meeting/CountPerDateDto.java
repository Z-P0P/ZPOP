package com.zpop.web.dto.admin.meeting;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CountPerDateDto {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int count;
}
