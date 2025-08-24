package com.example.leetarena.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leetarena.dtos.ActivePartyDTO;
import com.example.leetarena.models.ActiveParty;
import com.example.leetarena.repositories.ActivePartyRepository;

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
        party.setPartyDifficulty(dto.getDifficulty());
        party.setPartyPrize(dto.getPartyPrize());
        party.setEndTime(dto.getEndTime());

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

        return activePartyRepository.save(existingParty);
    }

    public void deleteParty(Integer id){
        activePartyRepository.deleteById(id);
        // TODO: message if user is deleted correctly
        // TODO: delete leetcode_set associated with the party
    }
}