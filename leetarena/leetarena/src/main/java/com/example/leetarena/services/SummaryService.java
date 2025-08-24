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

    public Summary getSummaryById(int summary_id){
        return summaryRepository.findById(summary_id).orElseThrow(() -> new RuntimeException("Summary not found"));
    }

    //Post Methods
    public Summary addSummary(Summary summary){

        if(summary.getSummary_description() == null || summary.getSummary_description().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        Summary newSummary = new Summary();
        newSummary.setSummary_description(summary.getSummary_description());
        newSummary.setActive_party(summary.getActive_party());

        return summaryRepository.save(newSummary);
    }

    //Patch method

    public Summary updateSummaryDescription(int summary_id,String summary_description){
        Summary summary = summaryRepository.findById(summary_id).orElseThrow(() -> new RuntimeException("Summary not found"));

        if(summary.getSummary_description() == null || summary.getSummary_description().isEmpty()){
            throw new RuntimeException("Summary description is empty");
        }

        if(!summary.getSummary_description().equals(summary_description)){
            summary.setSummary_description(summary_description);
        }

        return summaryRepository.save(summary);
    }

    //Put Method

    /*
    //Edit Summary_podium
     */

    //Delete Summary

    public void deleteSummary(int summary_id){
        if(!summaryRepository.existsById(summary_id)){
            throw new RuntimeException("Summary not found");
        }

        summaryRepository.deleteById(summary_id);
    }




}
