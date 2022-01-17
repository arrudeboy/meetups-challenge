package com.santander.tecnologia.service;

import com.santander.tecnologia.dto.UserMeetupAddDTO;
import com.santander.tecnologia.model.UserMeetup;

public interface UserMeetupService {

    UserMeetup enroll(UserMeetupAddDTO userMeetupDTO);
    UserMeetup checkIn(Long id);
}
