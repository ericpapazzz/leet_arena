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

    public User createUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserPasswordHash(user.getUserPasswordHash());
        newUser.setUserLeetcoins(0);

        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(newUser);
    }

    public User updateUser(Integer id, User user) {
        User existingUser = getUserById(id);
        // if data is not provided, stay with existing data
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }else{
            existingUser.setUsername(existingUser.getUsername());
        }

        if (user.getUserEmail() != null) {
            existingUser.setUserEmail(user.getUserEmail());
        }else{
            existingUser.setUserEmail(existingUser.getUserEmail());
        }

        if (user.getUserPasswordHash() != null) {
            existingUser.setUserPasswordHash(user.getUserPasswordHash());
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
