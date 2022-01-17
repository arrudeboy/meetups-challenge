package com.santander.tecnologia.scheduler;

import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.model.Location;
import com.santander.tecnologia.model.Meetup;
import com.santander.tecnologia.service.MeetupService;
import com.santander.tecnologia.service.WeatherReportService;
import com.santander.tecnologia.util.LocalDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application.properties")
public class WeatherReportTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherReportTask.class);

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private WeatherReportService openWeatherMapServiceImpl;

    @Scheduled(cron = "${scheduler.task.retrieve.weather.cron-exp}")
    public void retrieveWeatherForIncomingMeetups() {

        LOGGER.info("Started cronjob retrieveWeatherForIncomingMeetups...");
        Set<Location> meetupLocations = meetupService.getIncomingMeetups().stream().map(Meetup::getLocation).collect(Collectors.toSet());
        String[] nowDateTime = LocalDateUtil.getFromLocalDateTime(LocalDateTime.now()).split(" ");
        String nowDate = nowDateTime[0];
        String nowTime = nowDateTime[1];
        meetupLocations.forEach(l -> {
            try {
                openWeatherMapServiceImpl.retrieve(l, nowDate, nowTime);
            } catch (WeatherReportException e) {
                LOGGER.error(e.getMessage());
            }
        });
        LOGGER.info("Finished cronjob retrieveWeatherForIncomingMeetups.");
    }

}
