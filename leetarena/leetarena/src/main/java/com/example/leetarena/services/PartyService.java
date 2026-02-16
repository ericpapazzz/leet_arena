package com.example.leetarena.services;

import com.example.leetarena.dtos.CreateLeetcodeSetDTO;
import com.example.leetarena.dtos.PlayerDTO;
import com.example.leetarena.models.LeetcodeSet;
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
    private final LeetcodeSetService leetcodeSetService;
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    public PartyService(PartyRepository partyRepository, UserRepository userRepository, PlayerService playerService, LeetcodeSetService leetcodeSetService) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.playerService = playerService;
        this.leetcodeSetService = leetcodeSetService;
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
            throw new IllegalArgumentException("The party do not exist");
        }

        Party curr_party = waiting_party.get();
        if(!curr_party.getParty_status().equals("WAITING")){
            throw new IllegalArgumentException("This party was already completed or is currently in progress");
        }

        System.out.println(curr_party.getPlayers().getClass());


        //Before creating the party, we have to associated to the host_user
        if(dto.getParty_id() == null || dto.getPartyPrize().isEmpty() || dto.getDifficulty().isEmpty() || dto.getEndTime() == null){
            throw new IllegalArgumentException("There are empty values on the inputs data");
        }

        curr_party.setPartyDifficulty(dto.getDifficulty());
        curr_party.setPartyPrize(dto.getPartyPrize());
        curr_party.setEndTime(dto.getEndTime());

        if(curr_party.getPlayers().isEmpty() || curr_party.getPlayers().size() < 3){
            throw new IllegalArgumentException("There are no players enough in this party");
        }

        curr_party.setParty_status("ACTIVATED");
        CreateLeetcodeSetDTO newLeetcodeSet = new CreateLeetcodeSetDTO();
        newLeetcodeSet.setDifficulty(curr_party.getPartyDifficulty());
        newLeetcodeSet.setEndTime(curr_party.getEndTime());

        LeetcodeSet leetcodeSet = leetcodeSetService.createLeetcodeSet(newLeetcodeSet);
        if(leetcodeSet == null){
            throw new IllegalArgumentException("The leetcode set wasnt created correctly");
        }

        curr_party.setLeetcodeSet(leetcodeSet);
        return partyRepository.save(curr_party);
    }

    public Party getActivePartyById(Integer id){
        return partyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Party not found"));
    }

    public Party addNewPlayerToWaitingParty(Integer user_id,String invitation_code){

        //Check if the party exists
        Optional<Party> looking_for_party = partyRepository.getPartyByInvitationCode(invitation_code);

        if(!looking_for_party.isPresent()){
            throw new IllegalArgumentException("The party with : " + invitation_code + " invitation code wasnt found");
        }

        //Check if the party is in waiting status
        Party party = looking_for_party.get();
        if(!party.getParty_status().equals("WAITING")){
            throw new IllegalArgumentException("The party with is ended or is currently in progress");
        }

        //Check if there is more space
        if(party.getPlayers().size() >= 10){
            throw new IllegalArgumentException("The party is full");
        }

        //Verify if the user exists and is Logged (TODO)
        Optional<User> existing_user = userRepository.findById(user_id);
        if(!existing_user.isPresent()){
            throw new IllegalArgumentException("The user with : " + user_id + " wasnt found");
        }

        User user = existing_user.get();
        //TODO CHECK IF IS LOGGED
        //Adding the new Player to the party witht he auxiliary method
        if(party.getPlayers().stream().anyMatch(
                player -> player.getUser().getUser_id().equals(user.getUser_id())
        )){
            throw new IllegalArgumentException("The user with : " + user_id + " is already in progress");
        }

        if(!addNewPlayerToParty(party, user)){
            throw new IllegalArgumentException("The player wasnt created correctly");
        }

        return partyRepository.save(party);
    }

    public boolean addNewPlayerToParty(Party party, User user){

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerUsername(user.getUsername());
        playerDTO.setUserId(user.getUser_id());

        Player newPlayer = playerService.createPlayer(playerDTO);

        if(newPlayer == null){
            return false;
        }

        newPlayer.getParties().add(party);
        party.getPlayers().add(newPlayer);

        return true;
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