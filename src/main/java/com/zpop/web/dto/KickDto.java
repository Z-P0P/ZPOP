package com.zpop.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class KickDto {
    @Min(value = 1)
    @NotNull
    private int meetingId;
    @Min(value = 1)
    @NotNull
    private int participantId;
}