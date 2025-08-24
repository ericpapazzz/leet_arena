package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Player;
import repositories.PlayerRepository;
import dtos.PlayerDTO;
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

        newPlayer.setPlayer_username(dto.getPlayer_username());
        newPlayer.setPlayer_easys((byte) 0);
        newPlayer.setPlayer_mediums((byte) 0);
        newPlayer.setPlayer_hards((byte) 0);

        return playerRepository.save(newPlayer);
    }

    public Player updatePlayer(Integer id, PlayerDTO dto){
        Player existingPlayer = getPlayerById(id);

        // if data is not provided,stay with existing data
        if (dto.getPlayer_username() != null){
            existingPlayer.setPlayer_username(dto.getPlayer_username());
        }else{
            existingPlayer.setPlayer_username((existingPlayer.getPlayer_username()));
        }

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Integer id){
       playerRepository.deleteById(id);
        // TODO: message if user is deleted correctly
    }
}
