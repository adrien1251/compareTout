package com.eltae.compareTout.services;

import com.eltae.compareTout.converter.UserConverter;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.entities.User;
import com.eltae.compareTout.exceptions.NotFoundException;
import com.eltae.compareTout.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(), null);
    }

    public UserDto create(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        userDto = userConverter.entityToDto(this.userRepository.save(user));
        return userDto;
    }

    public UserDto update(UserDto userDto) throws NotFoundException {
        //TODO : Update user
        return userDto;
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id).map(this.userConverter::entityToDtoMinimumParams)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, id));
    }

    public UserDto getByToken(String token) {
        Optional<User> user = userRepository.findByResetToken(token);
        if(!user.isPresent()){
            throw new NotFoundException(HttpStatus.UNPROCESSABLE_ENTITY, token);
        }
        return this.userConverter.entityToDto(user.get());
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDto = new ArrayList<>();
        users.forEach(user -> userDto.add(this.userConverter.entityToDtoMinimumParams(user)));
        return userDto;
    }
}
