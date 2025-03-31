package com.backend.users.controller;

import com.backend.users.dto.UserDto;
import com.backend.users.model.Users;
import com.backend.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<Users> createUser(@RequestBody UserDto userDto) {
        Users users = new Users();
        BeanUtils.copyProperties(userDto, users);

        Users saveUsers = userRepository.save(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUsers);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        Optional<Users> findUser = userRepository.findById(id);

        if (findUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(findUser);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        Optional<Users> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }

        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

}
