package com.example.leetarena.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leetarena.dtos.UserDTO;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.UserRepository;
import com.example.leetarena.repositories.RecordRepository;
import com.example.leetarena.repositories.PlayerRepository;
import com.example.leetarena.repositories.ActivePartyRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.example.leetarena.models.Record;
import com.example.leetarena.models.Player;
import com.example.leetarena.models.ActiveParty;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final PlayerRepository playerRepository;
    private final ActivePartyRepository activePartyRepository;

    @Autowired
    public UserService(UserRepository userRepository, 
                      RecordRepository recordRepository,
                      PlayerRepository playerRepository,
                      ActivePartyRepository activePartyRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.playerRepository = playerRepository;
        this.activePartyRepository = activePartyRepository;
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

    @Transactional
    public void deleteUser(Integer id) {
        // first, delete all associated records
        deleteAllUserRecords(id);
        
        // then delete the user
        userRepository.deleteById(id);
        // TODO: message if user is deleted correctly
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
            activePartyRepository.deleteActivePartiesByUserId(userId);
            
            // Note: Products relationship is Many-to-Many, so we don't need to delete products
            // The relationship will be automatically removed when the user is deleted
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user records: " + e.getMessage());
        }
    }

    // Method to check if user has any associated data
    public boolean hasAssociatedData(Integer userId) {
        try {
            List<Record> userRecords = recordRepository.getRecordsByUserId(userId);
            List<Player> userPlayers = playerRepository.getPlayersByUserId(userId);
            List<ActiveParty> userActiveParties = activePartyRepository.getActivePartiesByUserId(userId);
            
            return !userRecords.isEmpty() || !userPlayers.isEmpty() || !userActiveParties.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
