package com.eltae.compareTout.converter;

import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserConverter extends GenericsConverter<User, UserDto> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .resetToken(user.getResetToken())
                .build();
    }

    public UserDto entityToDtoMinimumParams(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public List<UserDto> entityListToDtoListMinimumParams(List<User> entityList){
        List<UserDto> dtoList = new ArrayList<>();
        for(User entity : entityList){
            dtoList.add(entityToDtoMinimumParams(entity));
        }
        return dtoList;
    }

    @Override
    public User dtoToEntity(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());

        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .resetToken(userDto.getResetToken())
                .build();

    }
}
