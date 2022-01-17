package com.santander.tecnologia.controller;

import com.santander.tecnologia.dto.CreateMeetupDTO;
import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.model.Meetup;
import com.santander.tecnologia.model.WeatherReport;
import com.santander.tecnologia.service.MeetupService;
import com.santander.tecnologia.service.WeatherReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("meetups")
public class MeetupController {

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private WeatherReportService openWeatherMapServiceImpl;

    @GetMapping(value = "/{id}/beerPacksNeeded", produces = {"application/json"})
    public ResponseEntity<?> beerPacksNeeded(@PathVariable Long id) throws WeatherReportException {
        int beerPacksNeeded = meetupService.beerPacksNeeded(id);
        String jsonMessage = String.format("{\"beerPacksNeeded\": %d}", beerPacksNeeded);

        return ResponseEntity.ok(jsonMessage);
    }

    @GetMapping(value = "/{id}/weather", produces = {"application/json"})
    public ResponseEntity<?> meetupWeather(@PathVariable Long id) throws WeatherReportException {
        Meetup meetup = meetupService.getById(id);
        Optional<WeatherReport> optionalWeatherReport = openWeatherMapServiceImpl.retrieve(meetup.getLocation(), meetup.getDate(), meetup.getTime());
        if (optionalWeatherReport.isPresent()) {
            return ResponseEntity.ok(optionalWeatherReport.get());
        } else {
            throw new WeatherReportException("Could not get meetup weather from API Service neither from database");
        }
    }

    @PostMapping(value = "/create", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> createMeetup(@Valid @RequestBody CreateMeetupDTO createMeetupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetupService.create(createMeetupDTO));
    }

}
