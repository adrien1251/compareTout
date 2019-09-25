package com.eltae.compareTout.controllers;

import com.eltae.compareTout.SpringBasedTest;
import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.exceptions.NotFoundException;
import com.eltae.compareTout.services.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class UserControllerTest extends SpringBasedTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private String getUserDtoToJson(UserDto userDto) {
        return new Gson().toJson(userDto);
    }

    @Test
    public void createUserReturn201Test() throws Exception{
        //Entry
        UserDto userEntry = UserDto.builder().
                firstName("FirstName Test").
                lastName("LastName Test").
                email("Email").
                password("Password").
                resetToken("Reset token").build();

        //Expected
        String expected = getUserDtoToJson(userEntry);
        UserDto userExpected = userEntry.clone();
        userExpected.setId(1L);

        //Mocks
        Mockito.when(userService.create(userEntry)).thenReturn(userExpected);

        //Call
        this.mockMvc.perform(post(Routes.USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected))
                .andExpect(status().isCreated());

    }

    @Test
    public void createUserThrowExceptionTest() throws Exception{
        //Entry
        UserDto userEntry = UserDto.builder().build();

        //Expected
        String expected = getUserDtoToJson(userEntry);

        //Mocks
        Mockito.when(userService.create(userEntry)).thenThrow(RuntimeErrorException.class);

        //Call
        this.mockMvc.perform(post(Routes.USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void updateUserReturn200Test() throws Exception{
        //Entry
        UserDto userEntry = UserDto.builder().
                id(1L).
                firstName("FirstName Test").
                lastName("LastName Test").
                email("Email").
                password("Password").
                resetToken("Reset token").build();


        //Expected
        String expected = getUserDtoToJson(userEntry);

        //Mocks
        Mockito.when(userService.update(userEntry)).thenReturn(userEntry);

        //Call
        this.mockMvc.perform(put(Routes.USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected))
                .andExpect(status().isOk());

    }

    @Ignore
    @Test
    public void updateUserThrowNotFoundException() throws Exception{
        //Entry
        UserDto userEntry = UserDto.builder().build();

        //Expected
        String expected = getUserDtoToJson(userEntry);

        //Mocks
        Mockito.when(userService.create(userEntry)).thenThrow(new NotFoundException(HttpStatus.UNPROCESSABLE_ENTITY, 1L));

        //Call
        this.mockMvc.perform(post(Routes.USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getByTokenTest() throws Exception {
        //Entry
        String token = "Token";

        //Expected
        UserDto userExpected = UserDto.builder().resetToken(token).build();

        //Mocks
        Mockito.when(userService.getByToken(token)).thenReturn(userExpected);

        //Call
        this.mockMvc.perform(get(Routes.USERS + "/token/" + token))
                .andExpect(status().isOk());

    }

    @Test
    public void getByTokenTestThrowException() throws Exception {
        //Entry
        String token = "Token";

        //Expected

        //Mocks
        Mockito.when(userService.getByToken(token)).thenThrow(new NotFoundException(HttpStatus.UNPROCESSABLE_ENTITY, 1L));

        //Call
        this.mockMvc.perform(get(Routes.USERS + "/token/" + token))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        //Entry

        //Expected

        //Mocks
        Mockito.when(userService.getAllUsers()).thenReturn(new ArrayList<>());

        //Call
        this.mockMvc.perform(get(Routes.USERS + "/"))
                .andExpect(status().isOk());

    }


    @Test
    public void getByIdTest() throws Exception {
        //Entry
        Long id = 1L;

        //Expected
        UserDto userExpected = UserDto.builder().id(id).build();

        //Mocks
        Mockito.when(userService.getById(id)).thenReturn(userExpected);

        //Call
        this.mockMvc.perform(get(Routes.USERS + "/" + id))
                .andExpect(status().isOk());

    }

    @Test
    public void getByIdTestThrowException() throws Exception {
        //Entry
        Long id = 1L;

        //Mocks
        Mockito.when(userService.getById(id)).thenThrow(new NotFoundException(HttpStatus.NOT_FOUND, 1L));

        //Call
        this.mockMvc.perform(get(Routes.USERS + "/" + id))
                .andExpect(status().isNotFound());
    }
}
