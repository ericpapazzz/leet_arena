package com.example.leetarena.services;

import com.example.leetarena.models.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.leetarena.repositories.*;

import java.util.List;

@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;

    @Autowired
    public SummaryService(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    //Get Methods
    public List<Summary> getAllSummarys(){
        return summaryRepository.findAll();
    }

    public Summary getSummaryById(int summaryId){
        return summaryRepository.findById(summaryId).orElseThrow(() -> new RuntimeException("Summary not found"));
    }

    //Post Methods
    public Summary addSummary(Summary summary){

        if(summary.getSummaryDescription() == null || summary.getSummaryDescription().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        Summary newSummary = new Summary();
        newSummary.setSummaryDescription(summary.getSummaryDescription());
        newSummary.setActiveParty(summary.getActiveParty());

        return summaryRepository.save(newSummary);
    }

    //Patch method

    public Summary updateSummaryDescription(int summaryId,String summaryDescription){
        Summary summary = summaryRepository.findById(summaryId).orElseThrow(() -> new RuntimeException("Summary not found"));

        if(summary.getSummaryDescription() == null || summary.getSummaryDescription().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        if(!summary.getSummaryDescription().equals(summaryDescription)){
            summary.setSummaryDescription(summaryDescription);
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
