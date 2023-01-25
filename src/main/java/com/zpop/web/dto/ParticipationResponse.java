package com.zpop.web.dto;

import lombok.Getter;

@Getter
public class ParticipationResponse {
    private String contact;
    private boolean isClosed;

    public ParticipationResponse(String contact, boolean isClosed) {
        this.contact = contact;
        this.isClosed = isClosed;
    }
}
