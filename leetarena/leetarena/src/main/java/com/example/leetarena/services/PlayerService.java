package com.example.leetarena.services;

import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.PlayerDTO;
import com.example.leetarena.models.Player;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.PlayerRepository;
import com.example.leetarena.repositories.UserRepository;

import java.util.List;

@Service
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public PlayerService(PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<Player> getPlayersByUserId(Integer userId) {
        return playerRepository.getPlayersByUserId(userId);
    }

    public Player createPlayer(PlayerDTO dto){
        // Find the user by userId
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Player newPlayer = new Player();
        newPlayer.setPlayerUsername(dto.getPlayerUsername());
        newPlayer.setPlayerEasys((byte) 0);
        newPlayer.setPlayerMediums((byte) 0);
        newPlayer.setPlayerHards((byte) 0);
        newPlayer.setUser(user); // Associate player with user

        return playerRepository.save(newPlayer);
    }

    public Player updatePlayer(Integer id, PlayerDTO dto){
        Player existingPlayer = getPlayerById(id);

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Integer id){
       playerRepository.deleteById(id);
    }
}
