package com.santander.tecnologia.service;

import com.santander.tecnologia.dto.CreateMeetupDTO;
import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.model.Meetup;

import java.util.Set;

public interface MeetupService {

    Meetup getById(Long id);

    int beerPacksNeeded(Long id) throws WeatherReportException;

    Meetup create(CreateMeetupDTO createMeetupDTO);

    Set<Meetup> getIncomingMeetups();
}
