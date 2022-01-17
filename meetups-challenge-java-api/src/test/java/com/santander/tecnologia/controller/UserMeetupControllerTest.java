package com.santander.tecnologia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.tecnologia.MockedEntities;
import com.santander.tecnologia.dto.UserMeetupAddDTO;
import com.santander.tecnologia.model.UserMeetup;
import com.santander.tecnologia.service.UserMeetupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserMeetupController.class)
public class UserMeetupControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMeetupControllerTest.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMeetupService userMeetupService;

    @Test
    public void testEnrollUserToMeetup() {

        try {
            UserMeetup userMeetup = MockedEntities.getUserMeetup();
            UserMeetupAddDTO userMeetupAddDTO = new UserMeetupAddDTO(userMeetup.getUser().getId(), userMeetup.getMeetup().getId());
            given(userMeetupService.enroll(userMeetupAddDTO)).willReturn(userMeetup);

            mockMvc.perform(post("/meetupUsers/enroll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(userMeetupAddDTO)))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testCheckInUserOk() {

        try {
            UserMeetup userMeetup = MockedEntities.getUserMeetup();
            userMeetup.setCheckIn(true);
            given(userMeetupService.checkIn(1L)).willReturn(userMeetup);

            mockMvc.perform(put("/meetupUsers/{id}/checkIn"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.checkIn", is(true)));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testCheckInUserFailed() {

        try {
            when(userMeetupService.checkIn(Mockito.any())).thenThrow(EntityNotFoundException.class);

            mockMvc.perform(put("/meetupUsers/{id}/checkIn"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                    .andExpect(jsonPath("$.error_message", is("This User is not currently enrolled into that meetup")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


}
