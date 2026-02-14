package com.example.leetarena.services;

import com.example.leetarena.models.Summary;
import com.example.leetarena.models.Party;
import com.example.leetarena.dtos.SummaryDTO;
import org.springframework.stereotype.Service;
import com.example.leetarena.repositories.*;

import java.util.List;

@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final PartyRepository partyRepository;

    public SummaryService(SummaryRepository summaryRepository, PartyRepository partyRepository) {
        this.summaryRepository = summaryRepository;
        this.partyRepository = partyRepository;
    }

    //Get Methods
    public List<Summary> getAllSummarys(){
        return summaryRepository.findAll();
    }

    public Summary getSummaryById(int summaryId){
        return summaryRepository.findById(summaryId).orElseThrow(() -> new RuntimeException("Summary not found"));
    }

    //Post Methods
    public Summary create(SummaryDTO dto){
        Party party = partyRepository.findById(dto.getActivePartyId())
        .orElseThrow(() -> new RuntimeException("ActiveParty not found")); 

        if(dto.getSummaryDescription() == null || dto.getSummaryDescription().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        Summary summary = new Summary();
        summary.setSummaryDescription(dto.getSummaryDescription());
        summary.setParty(party);

        return summaryRepository.save(summary);
    }

    //Patch method

    public Summary updateSummaryDescription(Integer id, SummaryDTO dto){
        Summary summary = summaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Summary not found"));

        if(summary.getSummaryDescription() == null || summary.getSummaryDescription().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        if(!summary.getSummaryDescription().equals(dto.getSummaryDescription())){
            summary.setSummaryDescription(dto.getSummaryDescription());
        }

        return summaryRepository.save(summary);
    }

    //Put Method

    /*
    //Edit Summary_podium
     */

    //Delete Summary

    public void deleteSummary(int summaryId){
        if(!summaryRepository.existsById(summaryId)){
            throw new RuntimeException("Summary not found");
        }

        summaryRepository.deleteById(summaryId);
    }




}
