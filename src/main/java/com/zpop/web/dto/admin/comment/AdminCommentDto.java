
package com.zpop.web.dto.admin.comment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCommentDto {

    private int meetingId;
    private String meetingTitle;
    private int id;
    private String content;
    private int writerId;
    private int groupId;
    private String nickname;
    private int parentCommentId;
    private int parantCommentWriterId;
    private String parentCommentContent;
    @JsonFormat(pattern ="yyyy-MM-dd HH 시 mm 분")
    private Date createdAt;
    @JsonFormat(pattern ="yyyy-MM-dd HH 시 mm 분")
    private Date updatedAt;
    @JsonFormat(pattern ="yyyy-MM-dd HH 시 mm 분")
    private Date deletedAt;

}
