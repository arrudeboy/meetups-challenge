package com.santander.tecnologia.service.impl;

import com.santander.tecnologia.dto.CreateMeetupDTO;
import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.model.Meetup;
import com.santander.tecnologia.model.User;
import com.santander.tecnologia.model.WeatherReport;
import com.santander.tecnologia.repository.LocationRepository;
import com.santander.tecnologia.repository.MeetupRepository;
import com.santander.tecnologia.repository.UserRepository;
import com.santander.tecnologia.service.MeetupService;
import com.santander.tecnologia.service.NotificationService;
import com.santander.tecnologia.service.WeatherReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
public class MeetupServiceImpl implements MeetupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetupServiceImpl.class);

    private static final String NOT_FOUND_MSG = "No %s with id %d exists!";

    @Autowired
    private WeatherReportService openWeatherMapServiceImpl;

    @Autowired
    private NotificationService emailServiceImpl;

    @Autowired
    private MeetupRepository meetupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    private int beerPacksNeeded(double temperature, int meetupUsersQty) {

        double beerPacks = 0;
        if (temperature >= 20 && temperature < 24) {
            beerPacks = meetupUsersQty;
        } else if (temperature < 20) {
            beerPacks = meetupUsersQty * 0.75;
        } else if (temperature >= 24) {
            beerPacks = meetupUsersQty * 3;
        }

        return (int) Math.ceil(beerPacks / 6);
    }

    @Override
    @Cacheable("getMeetupBeerPacksNeeded")
    public int beerPacksNeeded(Long id) throws WeatherReportException {

        Meetup meetup = getById(id);
        Optional<WeatherReport> optionalWeatherReport = openWeatherMapServiceImpl.retrieve(meetup.getLocation(), meetup.getDate(), meetup.getTime());
        if (optionalWeatherReport.isPresent()) {
            return beerPacksNeeded(optionalWeatherReport.get().getTemperature(), meetup.getUsers().size());
        } else {
            throw new WeatherReportException("Sorry, we could not retrieve the meetup temperature. Please drink water");
        }
    }

    @Override
    public Meetup create(CreateMeetupDTO createMeetupDTO) {

        Meetup meetup = new Meetup();
        meetup.setDate(createMeetupDTO.getDate());
        meetup.setTime(createMeetupDTO.getTime());
        meetup.setDescription(createMeetupDTO.getDescription());
        Long locationId = createMeetupDTO.getLocationId();
        meetup.setLocation(locationRepository
                .findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(NOT_FOUND_MSG, "location", locationId))));

        meetupRepository.saveAndFlush(meetup);
        LOGGER.info(String.format("Saved new. %s", meetup.toString()));
        Set<User> users = userRepository.findByIdIn(createMeetupDTO.getSendInvitationUserIds());
        String[] userEmails = Arrays.copyOf(users.stream().map(User::getEmail).toArray(), users.size(), String[].class);
        try {
            emailServiceImpl.send(
                    userEmails,
                    String.format("Invitation to Meetup by %s", createMeetupDTO.getOwnerUsername()),
                    meetup.getDescription());
        } catch (MailException mailException) {
            LOGGER.error("Could not send emails: " + mailException.getMessage());
        }
        return meetup;
    }

    @Override
    public Set<Meetup> getIncomingMeetups() {
        return meetupRepository.getIncomingMeetups();
    }

    @Override
    public Meetup getById(Long id) {
        return meetupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format(NOT_FOUND_MSG, "meetup", id)));
    }

}
