package com.santander.tecnologia.service.impl;

import com.santander.tecnologia.dto.UserMeetupAddDTO;
import com.santander.tecnologia.model.UserMeetup;
import com.santander.tecnologia.repository.MeetupRepository;
import com.santander.tecnologia.repository.UserMeetupRepository;
import com.santander.tecnologia.repository.UserRepository;
import com.santander.tecnologia.service.UserMeetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserMeetupServiceImpl implements UserMeetupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMeetupServiceImpl.class);

    private static final String NOT_FOUND_MSG = "No %s with id %d exists!";

    @Autowired
    private UserMeetupRepository userMeetupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetupRepository meetupRepository;

    @Override
    public UserMeetup enroll(UserMeetupAddDTO userMeetupAddDTO) {

        UserMeetup userMeetup = new UserMeetup();
        long userId = userMeetupAddDTO.getUserId();
        long meetupId = userMeetupAddDTO.getMeetupId();
        userMeetup.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MSG, "user", userId))));
        userMeetup.setMeetup(meetupRepository.findById(meetupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND_MSG, "meetup", meetupId))));

        userMeetupRepository.saveAndFlush(userMeetup);
        LOGGER.info(String.format("Saved new. %s", userMeetup));
        return userMeetup;
    }

    @Override
    public UserMeetup checkIn(Long id) {

        UserMeetup userMeetup = userMeetupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("This User is not currently enrolled into that meetup"));
        userMeetup.setCheckIn(true);

        userMeetupRepository.saveAndFlush(userMeetup);
        LOGGER.info(String.format("Check in successfully. %s", userMeetup));
        return userMeetup;
    }

}
