package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dtos.ActivePartyDTO;
import models.ActiveParty;
import repositories.ActivePartyRepository;
import java.util.List;

@Service
public class ActivePartyService {

    private final ActivePartyRepository activePartyRepository;

    @Autowired
    public ActivePartyService(ActivePartyRepository activePartyRepository) {
        this.activePartyRepository = activePartyRepository;
    }

    public List<ActiveParty> getAllPartys() {
        return activePartyRepository.findAll();
    }

    public ActiveParty createParty(ActivePartyDTO dto){
        ActiveParty party = new ActiveParty();
        party.setParty_difficulty(dto.getDifficulty());
        party.setParty_prize(dto.getParty_prize());
        party.setEnd_time(dto.getEnd_time());

        return activePartyRepository.save(party);
    }

    public ActiveParty getActivePartyById(Integer id){
        return activePartyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Party not found"));
}

    public ActiveParty updateParty(Integer id, ActivePartyDTO dto){
        ActiveParty existingParty = getActivePartyById(id);
        // if data is not provided, stay with existing data
        if (dto.getDifficulty() != null){
            existingParty.setParty_difficulty(dto.getDifficulty());
        }else{
            existingParty.setParty_difficulty(existingParty.getParty_difficulty());
        }
        if (dto.getParty_prize() != null){
            existingParty.setParty_prize(dto.getParty_prize());
        }else{
            existingParty.setParty_prize(existingParty.getParty_prize());
        }
        if (dto.getEnd_time() != null){
            existingParty.setEnd_time(dto.getEnd_time());
        }else{
            existingParty.setEnd_time(existingParty.getEnd_time());
        }

        return activePartyRepository.save(existingParty);
    }

    public void deleteParty(Integer id){
        activePartyRepository.deleteById(id);
        // TODO: message if user is deleted correctly
        // TODO: delete leetcode_set associated with the party
    }
}