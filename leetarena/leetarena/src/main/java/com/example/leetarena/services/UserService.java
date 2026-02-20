package com.example.leetarena.services;

import com.example.leetarena.dtos.LeetcodeProfileDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leetarena.dtos.UserDTO;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.UserRepository;
import com.example.leetarena.repositories.RecordRepository;
import com.example.leetarena.repositories.PlayerRepository;
import com.example.leetarena.repositories.PartyRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       RecordRepository recordRepository,
                       PlayerRepository playerRepository,
                       PartyRepository partyRepository) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.playerRepository = playerRepository;
        this.partyRepository = partyRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
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

        LeetcodeProfileDTO profileDTO = validateLeetcodeAccount(dto.getUsername());
        newUser.setUsername(profileDTO.getUsername());
        newUser.setUserAvatar(profileDTO.getUserAvatar());
        newUser.setLeetRank(profileDTO.getLeetRank());

        return userRepository.save(newUser);
    }

    public LeetcodeProfileDTO validateLeetcodeAccount(String leetcode_username){
        //1. Check if is not already register
        Optional<User> temp_user = userRepository.findByUsername(leetcode_username);
        if(temp_user.isPresent()){
            throw new IllegalArgumentException("This user already exits in the database");
        }

        //2. Call the LeetcodeAPI to find it
        String url = "https://leetcode-api-pied.vercel.app/user/" + leetcode_username;
        String response = restTemplate.getForObject(url, String.class);
        LeetcodeProfileDTO newLeetcodeProfileDTO = new LeetcodeProfileDTO();

        try {
            JsonNode root = objectMapper.readTree(response);
            if(root.has("detail")){
                throw new IllegalArgumentException("The user wasnt find it in the leetcode DB");
            }

            if(root.has("username")){
                newLeetcodeProfileDTO.setUsername(root.get("username").asText());
            }

            JsonNode profile = root.get("profile");

            if(profile.has("userAvatar")){
                newLeetcodeProfileDTO.setUserAvatar(profile.get("userAvatar").asText());
            }

            if(profile.has("ranking")){
                newLeetcodeProfileDTO.setLeetRank(profile.get("ranking").asLong());
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return newLeetcodeProfileDTO;
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
