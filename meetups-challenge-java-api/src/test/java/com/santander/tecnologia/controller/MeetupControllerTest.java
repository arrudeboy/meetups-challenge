package com.santander.tecnologia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.tecnologia.MockedEntities;
import com.santander.tecnologia.dto.CreateMeetupDTO;
import com.santander.tecnologia.model.Meetup;
import com.santander.tecnologia.model.WeatherReport;
import com.santander.tecnologia.service.MeetupService;
import com.santander.tecnologia.service.WeatherReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(MeetupController.class)
public class MeetupControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetupControllerTest.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetupService meetupService;

    @MockBean
    private WeatherReportService openWeatherMapServiceImpl;

    @Test
    public void testBeerPacksNeeded() {

        try {
            given(meetupService.beerPacksNeeded(1L)).willReturn(5);
            mockMvc.perform(get("/meetups/{id}/beerPacksNeeded", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.beerPacksNeeded", is(5)));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testMeetupWeather() {

        try {
            Optional<WeatherReport> optionalWeatherReport = MockedEntities.getWeatherReport();
            Meetup meetup = MockedEntities.getMeetup();
            given(meetupService.getById(1L)).willReturn(meetup);
            given(openWeatherMapServiceImpl
                    .retrieve(meetup.getLocation(), meetup.getDate(), meetup.getTime()))
                    .willReturn(optionalWeatherReport);

            mockMvc.perform(get("/meetups/{id}/weather", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.date", is("2022-01-16")))
                    .andExpect(jsonPath("$.time", is("16:00:00")))
                    .andExpect(jsonPath("$.temperature", is(25.0)))
                    .andExpect(jsonPath("$.temperatureMax", is(29.3)))
                    .andExpect(jsonPath("$.temperatureMin", is(16.6)))
                    .andExpect(jsonPath("$.countryCode", is("AR")))
                    .andExpect(jsonPath("$.city", is("Buenos Aires")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testCreateMeetup() {

        try {
            Meetup meetup = MockedEntities.getMeetup();
            CreateMeetupDTO createMeetupDTO = new CreateMeetupDTO(
                    "arturo.chari", Collections.singleton(2L),
                    meetup.getDate(), meetup.getTime(), meetup.getDescription(),
                    MockedEntities.getLocations().get(0).getId());
            given(meetupService.create(createMeetupDTO)).willReturn(meetup);

            mockMvc.perform(post("/meetups/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(createMeetupDTO)))
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
