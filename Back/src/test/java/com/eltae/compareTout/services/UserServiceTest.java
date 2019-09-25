package com.eltae.compareTout.services;


import com.eltae.compareTout.SpringBasedTest;
import com.eltae.compareTout.controllers.UserController;
import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.exceptions.NotFoundException;
import com.eltae.compareTout.repositories.UserRepository;
import com.eltae.compareTout.security.Token;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * {@link UserController}'s unit tests
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest extends SpringBasedTest {
    @SpyBean
    @Autowired
    private UserService userService;

    @SpyBean
    @Autowired
    private UserConverter userConverter;

    @SpyBean
    @Autowired
    private Token token;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User createEntityUser(Long i) {
        return User.builder()
                .id(i)
                .email("test" + i + "@test" + i + ".fr")
                .firstName("test" + i)
                .lastName("TEST" + i)
                .password("password" + i + "Test").build();
    }

    private UserDto createDtoUser(Long i) {
        return UserDto.builder()
                .id(i)
                .email("test" + i + "@test" + i + ".fr")
                .firstName("test" + i)
                .lastName("TEST" + i)
                .password("password" + i + "Test").build();
    }

    public boolean assertEqualsEntityAndDto(User entity, UserDto dto) {
        return
                entity.getId().equals(dto.getId()) &&
                        entity.getLastName().equals(dto.getLastName()) &&
                        entity.getFirstName().equals(dto.getFirstName()) &&
                        entity.getPassword().equals(dto.getPassword()) &&
                        entity.getResetToken().equals(dto.getResetToken());
    }

    @Test
    public void createdSimpleUser() throws CloneNotSupportedException {
        //Entry
        UserDto userEntry = UserDto.builder()
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName").build();

        //Expected
        User userReturnByEntity = this.userConverter.dtoToEntity(userEntry);

        User entryUserSave = userReturnByEntity.clone();

        User userReturnBySave = entryUserSave.clone();
        userReturnBySave.setId(1L);

        UserDto userExpected = UserDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .build();
        //Mocks
        Mockito.when(this.bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("randomPassword");
        Mockito.when(this.userRepository.save(entryUserSave)).thenReturn(userReturnBySave);

        //Call
        UserDto userEffective = userService.create(userEntry);

        //Asset
        assertEquals(userExpected, userEffective);
    }

    @Ignore
    @Test
    public void updateUser() throws CloneNotSupportedException {
        //Entry
        UserDto userEntry = UserDto.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName-ToUpdate")
                .lastName("testLastName")
                .password("passwordTest").build();

        //Expected
        User expectedDbUserToUpdate = User.builder()
                .id(1L)
                .email("test@test.fr")
                .firstName("testFirstName")
                .lastName("testLastName")
                .password("passwordTest").build();

        User entryUserUpdated = expectedDbUserToUpdate.clone();
        entryUserUpdated.setFirstName("testFirstName-ToUpdate");


        //Mocks
        Mockito.when(this.userRepository.findById(userEntry.getId())).thenReturn(Optional.of(expectedDbUserToUpdate));
        Mockito.when(this.userRepository.save(entryUserUpdated)).thenReturn(entryUserUpdated);

        //Call
        UserDto userEffective = userService.update(userEntry);

        //Asset
        Mockito.verify(this.userRepository, Mockito.times(1)).save(entryUserUpdated);
        assertEquals(userEntry, userEffective);
    }

    @Ignore
    @Test(expected = NotFoundException.class)
    public void updateUserNotFound() {
        //Mocks
        Mockito.when(this.userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        //Call
        userService.update(UserDto.builder().id(1L).build());
    }

    @Test
    public void getAllUsersTest() {
        //Expected
        List<User> expectedUserInRepoList = new ArrayList<>();
        expectedUserInRepoList.add(createEntityUser(1L));
        expectedUserInRepoList.add(createEntityUser(2L));

        List<UserDto> expectedUserDtoList = new ArrayList<>();
        for (User user : expectedUserInRepoList) {
            expectedUserDtoList.add(this.userConverter.entityToDtoMinimumParams(user));
        }

        //Mocks
        Mockito.when(this.userRepository.findAll()).thenReturn(expectedUserInRepoList);

        //Call
        List<UserDto> userEffective = userService.getAllUsers();

        //Asset
        assertEquals(expectedUserDtoList, userEffective);
    }

    @Test
    public void getById() {
        //Entry
        Long id = 1L;
        //Expected
        User expectedUserInRepo = createEntityUser(id);

        UserDto expected = this.userConverter.entityToDtoMinimumParams(expectedUserInRepo);

        //Mocks
        Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.of(expectedUserInRepo));

        //Call
        UserDto userEffective = userService.getById(id);

        //Asset
        assertEquals(expected, userEffective);
    }

    @Test(expected = NotFoundException.class)
    public void getByIdThrow404NotFound() {
        //Entry
        Long id = 1L;

        //Expected

        //Mocks
        Mockito.when(this.userRepository.findById(id)).thenReturn(Optional.empty());

        //Call
        userService.getById(id);
    }

    @Test
    public void getByTokenTest() {
        //Entry
        String entry = "token";

        //Expected
        User userExpectedDbFindByResetToken = User.builder().resetToken(entry).email("email").build();

        UserDto expected = this.userConverter.entityToDto(userExpectedDbFindByResetToken);

        //Mocks
        Mockito.when(this.userRepository.findByResetToken(entry)).thenReturn(Optional.of(userExpectedDbFindByResetToken));

        //Call
        UserDto effective = userService.getByToken(entry);

        //Assert
        assertEquals(expected, effective);
    }

    @Test(expected = NotFoundException.class)
    public void getByTokenNotFoundException() {
        //Entry
        String entry = "token";
        //Expected

        //Mocks
        Mockito.when(this.userRepository.findByResetToken(entry)).thenReturn(Optional.empty());

        //Call
        userService.getByToken(entry);
    }

}
