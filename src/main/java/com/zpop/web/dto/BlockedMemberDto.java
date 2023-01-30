package com.zpop.web.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlockedMemberDto {
    private int reportedId;
    private String reportedType;
    private Date blockedAt;
    private Date releasedAt;
}
