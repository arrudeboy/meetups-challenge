package com.santander.tecnologia.dto;

import javax.validation.constraints.NotNull;

public class UserMeetupAddDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long meetupId;

    public UserMeetupAddDTO() {
    }

    public UserMeetupAddDTO(@NotNull Long userId, @NotNull Long meetupId) {
        this.userId = userId;
        this.meetupId = meetupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeetupId() {
        return meetupId;
    }

    public void setMeetupId(Long meetupId) {
        this.meetupId = meetupId;
    }
}
