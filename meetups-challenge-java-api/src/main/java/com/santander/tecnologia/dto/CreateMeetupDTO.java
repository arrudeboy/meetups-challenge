package com.santander.tecnologia.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class CreateMeetupDTO {

    @NotBlank
    private String ownerUsername;

    @NotEmpty
    private Set<Long> sendInvitationUserIds;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String date;

    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$")
    private String time;

    private String description;

    @NotNull
    private Long locationId;

    public CreateMeetupDTO() {
    }

    public CreateMeetupDTO(String ownerUsername, Set<Long> sendInvitationUserIds,
                           @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
                           @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$") String time,
                           String description, @NotNull Long locationId) {

        this.ownerUsername = ownerUsername;
        this.sendInvitationUserIds = sendInvitationUserIds;
        this.date = date;
        this.time = time;
        this.description = description;
        this.locationId = locationId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Set<Long> getSendInvitationUserIds() {
        return sendInvitationUserIds;
    }

    public void setSendInvitationUserIds(Set<Long> sendInvitationUserIds) {
        this.sendInvitationUserIds = sendInvitationUserIds;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
