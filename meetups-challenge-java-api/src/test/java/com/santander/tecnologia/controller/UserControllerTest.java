package com.santander.tecnologia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.tecnologia.MockedEntities;
import com.santander.tecnologia.dto.UserAddDTO;
import com.santander.tecnologia.model.User;
import com.santander.tecnologia.service.UserService;
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
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() {

        try {
            given(userService.getAll()).willReturn(MockedEntities.getUsers());
            mockMvc.perform(get("/users/all").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].username", is("arturo.chari")))
                    .andExpect(jsonPath("$[0].email", is("arturofelixchari@gmail.com")))
                    .andExpect(jsonPath("$[0].password", is("1234567890")))
                    .andExpect(jsonPath("$[1].username", is("felix.chari")))
                    .andExpect(jsonPath("$[1].email", is("arturo.chari@ibm.com")))
                    .andExpect(jsonPath("$[1].password", is("1234567890")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testGetUserById() {

        try {
            given(userService.getById(1L)).willReturn(MockedEntities.getUsers().get(0));
            mockMvc.perform(get("/users/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.username", is("arturo.chari")))
                    .andExpect(jsonPath("$.email", is("arturofelixchari@gmail.com")))
                    .andExpect(jsonPath("$.password", is("1234567890")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testAddUserOk() {

        try {
            User user = MockedEntities.getUsers().get(0);
            UserAddDTO userAddDTO = new UserAddDTO();
            userAddDTO.setEmail(user.getEmail());
            userAddDTO.setPassword(user.getPassword());
            userAddDTO.setUsername(user.getUsername());
            mockMvc.perform(post("/users/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(userAddDTO)))
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testAddUserFailedDueToValidations() {

        try {
            User user = MockedEntities.getInvalidUser();
            UserAddDTO userAddDTO = new UserAddDTO();
            userAddDTO.setEmail(user.getEmail());
            userAddDTO.setPassword(user.getPassword());
            userAddDTO.setUsername(user.getUsername());
            mockMvc.perform(post("/users/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(userAddDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.username", is("size must be between 2 and 30")))
                    .andExpect(jsonPath("$.password", is("size must be between 10 and 50")))
                    .andExpect(jsonPath("$.email", is("must be a well-formed email address")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testUpdateUserOk() {

        try {
            User user = MockedEntities.getUsers().get(1);
            User updatedUser = MockedEntities.getUsers().get(1);
            updatedUser.setEmail("felix.chari@gmail.com");
            updatedUser.setPassword("asdwer456lgk9");

            when(userService.add(Mockito.any())).thenReturn(user);
            when(userService.update(Mockito.any())).thenReturn(updatedUser);

            mockMvc.perform(post("/users/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(user)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.email", is("arturo.chari@ibm.com")))
                    .andExpect(jsonPath("$.password", is("1234567890")));

            mockMvc.perform(put("/users/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(updatedUser)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(2)))
                    .andExpect(jsonPath("$.email", is("felix.chari@gmail.com")))
                    .andExpect(jsonPath("$.password", is("asdwer456lgk9")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testUserUpdateFailedDueToUserDoesntExists() {

        try {
            User updatedUser = MockedEntities.getUsers().get(1);
            updatedUser.setId(3L);

            when(userService.update(Mockito.any())).thenThrow(EntityNotFoundException.class);

            mockMvc.perform(put("/users/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON_MAPPER.writeValueAsString(updatedUser)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Test
    public void testDeleteUserOk() {

        try {
            mockMvc.perform(delete("/users/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertTrue(result.getResponse().getContentAsString()
                            .contains("User with id 1 has been deleted")));

            verify(userService, times(1)).delete(1L);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void testDeleteUserFailedDueToUserDoesntExists() {

        try {

            String messageError = "No user with id 3 exists!";
            doThrow(new EntityNotFoundException(messageError)).when(userService).delete(3L);

            mockMvc.perform(delete("/users/{id}", 3L))
                    .andExpect(status().isInternalServerError())
                    .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains(messageError)));

            verify(userService, times(1)).delete(3L);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
