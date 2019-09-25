package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.UserDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.USERS)
public class UserController extends ExceptionCatcher {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(200).body(this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getMinimumInfoUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(201).body(this.userService.create(userDto));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(200).body(this.userService.update(userDto));
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<UserDto> getByToken(@PathVariable String token) {
        return ResponseEntity.status(200).body(this.userService.getByToken(token));
    }
}
