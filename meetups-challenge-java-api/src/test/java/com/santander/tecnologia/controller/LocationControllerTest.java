package com.santander.tecnologia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.tecnologia.MockedEntities;
import com.santander.tecnologia.dto.CreateLocationDTO;
import com.santander.tecnologia.model.Location;
import com.santander.tecnologia.service.LocationService;
import org.hibernate.exception.ConstraintViolationException;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LocationController.class)
public class LocationControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationControllerTest.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    public void testGetAllLocations() {

        try {
            given(locationService.getAll()).willReturn(MockedEntities.getLocations());
            mockMvc.perform(get("/locations/all").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].countryCode", is("AR")))
                    .andExpect(jsonPath("$[0].city", is("Buenos Aires")))
                    .andExpect(jsonPath("$[1].countryCode", is("UY")))
                    .andExpect(jsonPath("$[1].city", is("Montevideo")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testAddLocationOk() {

        try {
            Location location = MockedEntities.getLocations().get(0);
            CreateLocationDTO createLocationDTO = new CreateLocationDTO(location.getCountryCode(), location.getCity());
            mockMvc.perform(post("/locations/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(createLocationDTO)))
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testAddLocationFailedDueToConstraintViolationException() {

        try {
            Location location = MockedEntities.getLocations().get(0);
            CreateLocationDTO createLocationDTO = new CreateLocationDTO(location.getCountryCode(), location.getCity());

            when(locationService.add(Mockito.any())).thenThrow(ConstraintViolationException.class);

            mockMvc.perform(post("/locations/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(createLocationDTO)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testUpdateLocationOk() {

        try {
            Location location = MockedEntities.getLocations().get(1);
            Location updatedLocation = MockedEntities.getLocations().get(1);
            updatedLocation.setCountryCode("CL");
            updatedLocation.setCity("Bogota");

            when(locationService.add(Mockito.any())).thenReturn(location);
            when(locationService.update(Mockito.any())).thenReturn(updatedLocation);

            mockMvc.perform(post("/locations/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(location)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.countryCode", is("UY")))
                    .andExpect(jsonPath("$.city", is("Montevideo")));

            mockMvc.perform(put("/locations/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(updatedLocation)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.countryCode", is("CL")))
                    .andExpect(jsonPath("$.city", is("Bogota")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testLocationUpdateFailedDueToLocationDoesntExists() {

        try {
            Location updatedLocation = MockedEntities.getLocations().get(1);
            updatedLocation.setId(3L);

            when(locationService.update(Mockito.any())).thenThrow(EntityNotFoundException.class);

            mockMvc.perform(put("/locations/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(updatedLocation)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Test
    public void testDeleteLocationOk() {

        try {
            mockMvc.perform(delete("/locations/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                            .contains("Location with id 1 has been deleted")));

            verify(locationService, times(1)).delete(1L);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testDeleteLocationFailedDueToLocationDoesntExists() {

        try {

            String messageError = "No location with id 3 exists!";
            doThrow(new EntityNotFoundException(messageError)).when(locationService).delete(3L);

            mockMvc.perform(delete("/locations/{id}", 3L))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains(messageError)));

            verify(locationService, times(1)).delete(3L);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
