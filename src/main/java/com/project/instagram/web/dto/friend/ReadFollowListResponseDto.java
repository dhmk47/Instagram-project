package com.project.instagram.web.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ReadFollowListResponseDto {
    private int followCode;
    private int fromUserCode;
    private boolean followingFlag;
    private String fromUserName;
    private String toUserName;
    private int toDetailCode;
    private int fromDetailCode;
    private String toUserId;
    private String fromUserId;
    private String toUserNickname;
    private String fromUserNickname;
    private String createDate;
    private String updateDate;

    public ReadFollowListResponseDto(){}

    public ReadFollowListResponseDto(int followCode, int fromUserCode, int followingFlag, String fromUserName, String toUserName, int toDetailCode, int fromDetailCode, String fromUserId,
                                     String toUserId, String toUserNickname, String fromUserNickname, LocalDateTime createDate, LocalDateTime updateDate) {
        this.followCode = followCode;
        this.fromUserCode = fromUserCode;
        this.followingFlag = followingFlag > 0;
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.toDetailCode = toDetailCode;
        this.toUserId = toUserId;
        this.fromDetailCode = fromDetailCode;
        this.fromUserId = fromUserId;
        this.toUserNickname = toUserNickname;
        this.fromUserNickname = fromUserNickname;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.updateDate = updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}