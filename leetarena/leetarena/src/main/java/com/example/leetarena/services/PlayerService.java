package com.example.leetarena.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.PlayerDTO;
import com.example.leetarena.models.Player;
import com.example.leetarena.repositories.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public Player createPlayer(PlayerDTO dto){
        Player newPlayer = new Player();

        newPlayer.setPlayerUsername(dto.getPlayerUsername());
        newPlayer.setPlayerEasys((byte) 0);
        newPlayer.setPlayerMediums((byte) 0);
        newPlayer.setPlayerHards((byte) 0);

        return playerRepository.save(newPlayer);
    }

    public Player updatePlayer(Integer id, PlayerDTO dto){
        Player existingPlayer = getPlayerById(id);

        // if data is not provided,stay with existing data
        if (dto.getPlayerUsername() != null){
            existingPlayer.setPlayerUsername(dto.getPlayerUsername());
        }else{
            existingPlayer.setPlayerUsername((existingPlayer.getPlayerUsername()));
        }

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Integer id){
       playerRepository.deleteById(id);
        // TODO: message if user is deleted correctly
    }
}
