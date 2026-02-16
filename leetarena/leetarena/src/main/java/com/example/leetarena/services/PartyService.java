package com.example.leetarena.services;

import com.example.leetarena.dtos.PlayerDTO;
import com.example.leetarena.models.Player;
import com.example.leetarena.models.User;
import com.example.leetarena.repositories.PlayerRepository;
import com.example.leetarena.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.PartyDTO;
import com.example.leetarena.models.Party;
import com.example.leetarena.repositories.PartyRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final PlayerService playerService;
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    public PartyService(PartyRepository partyRepository, UserRepository userRepository, PlayerService playerService) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.playerService = playerService;
    }

    public List<Party> getAllPartys() {
        return partyRepository.findAll();
    }

    public Party createPartyDefault(Integer admin_id){
        Optional<User> admin_party = userRepository.findById(admin_id); //Check if the user exists in the database
        if(!admin_party.isPresent()){
            throw new IllegalArgumentException("The admin not exist");
        }

        Party party = new Party();
        party.setUser(admin_party.get());

        boolean saved_code = false;
        while (!saved_code){ //This works for prevent duplicate invitation_codes

            int new_code = random.nextInt(1000000);
            String newCode = String.format("%06d",new_code);

            try{
                party.setInvitation_code(newCode);
                //TODO an extra verification if the code of the party is inactive, reusite and delete for the previous one
                saved_code = true;
            } catch (DataIntegrityViolationException e){
                System.out.println("Something went wrong : " + e.getMessage());
            }

        }

        PlayerDTO newPlayer = new PlayerDTO();
        newPlayer.setPlayerUsername(admin_party.get().getUsername());
        newPlayer.setUserId(admin_id);

        Player adminPlayer = playerService.createPlayer(newPlayer);

        if(adminPlayer == null){
            throw new IllegalArgumentException("The player wasnt created correctly");
        }

        List<Player> players = new ArrayList<>();
        players.add(adminPlayer);
        party.setPlayers(players);
        party.setParty_status("WAITING");
        return partyRepository.save(party);

    }

    public Party completeParty(PartyDTO dto){
        Optional<Party> waiting_party = partyRepository.findById(dto.getParty_id());

        if(!waiting_party.isPresent()){
            throw new IllegalArgumentException("The host not exist");
        }

        Party curr_party = waiting_party.get();
        if(!curr_party.getParty_status().equals("WAITING")){
            throw new IllegalArgumentException("This party was already completed");
        }

        System.out.println(curr_party.getPlayers().getClass());


        //Before creating the party, we have to associated to the host_user
        curr_party.setPartyDifficulty(dto.getDifficulty());
        curr_party.setPartyPrize(dto.getPartyPrize());
        curr_party.setEndTime(dto.getEndTime());

        for(PlayerDTO playerDTO : dto.getPlayers()){
            Player newPlayer = playerService.createPlayer(playerDTO);


            if(newPlayer == null){
                throw new IllegalArgumentException("The player wasnt created correctly");
            }

            System.out.println(newPlayer.getPlayerUsername());
            System.out.println(newPlayer.getPlayer_id());
            newPlayer.getParties().add(curr_party);
            curr_party.getPlayers().add(newPlayer);
        }

        System.out.println(curr_party.getPlayers().size());
        if(curr_party.getPlayers().isEmpty() || curr_party.getPlayers().size() < 3){
            throw new IllegalArgumentException("There are no players enough in this party");
        }

        curr_party.setParty_status("ACTIVATED");
        return partyRepository.save(curr_party);
    }

    public Party getActivePartyById(Integer id){
        return partyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Party not found"));
    }

    public Party updateParty(Integer id, PartyDTO dto){
        Party existingParty = getActivePartyById(id);
        // if data is not provided, stay with existing data
        if (dto.getDifficulty() != null){
            existingParty.setPartyDifficulty(dto.getDifficulty());
        }else{
            existingParty.setPartyDifficulty(existingParty.getPartyDifficulty());
        }
        if (dto.getPartyPrize() != null){
            existingParty.setPartyPrize(dto.getPartyPrize());
        }else{
            existingParty.setPartyPrize(existingParty.getPartyPrize());
        }
        if (dto.getEndTime() != null){
            existingParty.setEndTime(dto.getEndTime());
        }else{
            existingParty.setEndTime(existingParty.getEndTime());
        }

        return partyRepository.save(existingParty);
    }

    public void deleteParty(Integer party_id){

        Optional<Party> activePartyOptional = partyRepository.findById(party_id);
        if(!activePartyOptional.isPresent()){
            throw new IllegalArgumentException("Party with id : " + party_id + " not found");
        }
        Party party = activePartyOptional.get();

        party.setLeetcodeSet(null);
        partyRepository.deleteActivePartiesByUserId(party.getParty_id());

        // TODO: message if user is deleted correctly
        // TODO: delete leetcode_set assoc}iated with the party
    }
}