package com.example.leetarena.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leetarena.dtos.UserDTO;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.UserRepository;
import com.example.leetarena.repositories.RecordRepository;
import com.example.leetarena.repositories.PlayerRepository;
import com.example.leetarena.repositories.PartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;

    public UserService(UserRepository userRepository, 
                      RecordRepository recordRepository,
                      PlayerRepository playerRepository,
                      PartyRepository partyRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.playerRepository = playerRepository;
        this.partyRepository = partyRepository;
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

        newUser.setUserEmail(dto.getEmail());
        newUser.setUserLeetcoins(0);

        // Check if email already exists
        if (userRepository.findByUserEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(newUser);
    }


    //Todo function to add leetcoins when a user wins
    public void addLeetcoins(Integer user_id, int leetcoins){
        Optional<User> user = userRepository.findById(user_id);

        if(!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User curr_user = user.get();
        curr_user.setUserLeetcoins(user.get().getUserLeetcoins() + leetcoins);

        userRepository.save(curr_user);
    }

    public User updateUser(Integer id, UserDTO dto) {
        User existingUser = getUserById(id);
        // if data is not provided, stay with existing data

        if (dto.getEmail() != null) {
            existingUser.setUserEmail(dto.getEmail());
        }else{
            existingUser.setUserEmail(existingUser.getUserEmail());
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Integer id) {
        // first, delete all associated records
        deleteAllUserRecords(id);
        
        // then delete the user
        userRepository.deleteById(id);
    }

    // delete all records associated to the user when user is delete
    @Transactional
    public void deleteAllUserRecords(Integer userId) {
        try {
            // delete all records associated with the user
            recordRepository.deleteRecordsByUserId(userId);
            
            // delete all players associated with the user
            playerRepository.deletePlayersByUserId(userId);
            
            // delete all active parties associated with the user
            partyRepository.deleteActivePartiesByUserId(userId);
            
            // Note: Products relationship is Many-to-Many, so we don't need to delete products
            // The relationship will be automatically removed when the user is deleted
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user records: " + e.getMessage());
        }
    }
}
