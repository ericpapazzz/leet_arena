package com.example.leetarena.services;

import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.UserDTO;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(UserDTO dto) {
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setUserEmail(dto.getEmail());
        newUser.setUserPasswordHash(dto.getPasswordHash());
        newUser.setUserLeetcoins(0);

        // Check if username or email already exists
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByUserEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(newUser);
    }

    public User updateUser(Integer id, UserDTO dto) {
        User existingUser = getUserById(id);
        // if data is not provided, stay with existing data
        if (dto.getUsername() != null) {
            existingUser.setUsername(dto.getUsername());
        }else{
            existingUser.setUsername(existingUser.getUsername());
        }

        if (dto.getEmail() != null) {
            existingUser.setUserEmail(dto.getEmail());
        }else{
            existingUser.setUserEmail(existingUser.getUserEmail());
        }

        if (dto.getPasswordHash() != null) {
            existingUser.setUserPasswordHash(dto.getPasswordHash());
        }else{
            existingUser.setUserPasswordHash(existingUser.getUserPasswordHash());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
        // TODO: message if user is deleted correctly
        // TODO: delete all records associated with the user
    }

}
