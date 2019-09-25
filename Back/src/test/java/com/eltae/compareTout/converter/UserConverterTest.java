package com.eltae.compareTout.converter;

import com.eltae.compareTout.SpringBasedTest;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserConverterTest extends SpringBasedTest {

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    @Autowired
    private UserConverter userConverter;

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

    @Test
    public void entityToDtoTest() {
        //Entry
        User entry = createEntityUser(1L);

        //Expected
        UserDto expected = createDtoUser(1L);

        //Call
        UserDto effective = this.userConverter.entityToDto(entry);

        //Asset
        assertEquals(expected, effective);
    }

    @Test
    public void entityToDtoMinimumParamsTest() {
        //Entry
        User entry = createEntityUser(1L);

        //Expected
        UserDto expected = UserDto.builder().
                id(entry.getId())
                .firstName(entry.getFirstName())
                .lastName(entry.getLastName())
                .email(entry.getEmail()).build();

        //Call
        UserDto effective = this.userConverter.entityToDtoMinimumParams(entry);

        //Asset
        assertEquals(expected, effective);
    }

    @Test
    public void dtoToEntityTest() {
        //Entry
        UserDto entry = createDtoUser(1L);

        //Expected
        User expected = createEntityUser(1L);

        //Mock
        Mockito.when(userRepository.findById(entry.getId())).thenReturn(Optional.of(expected));

        //Call
        User effective = this.userConverter.dtoToEntity(entry);

        //Asset
        assertEquals(expected, effective);
    }

    @Test
    public void dtoToEntityWhenNoIdTest() {
        //Entry
        UserDto entry = createDtoUser(1L);
        entry.setId(null);

        //Expected
        User expected = createEntityUser(1L);
        expected.setId(null);

        //Mock
        Mockito.when(userRepository.findById(entry.getId())).thenReturn(Optional.of(expected));

        //Call
        User effective = this.userConverter.dtoToEntity(entry);

        //Asset
        assertEquals(expected, effective);
    }
}