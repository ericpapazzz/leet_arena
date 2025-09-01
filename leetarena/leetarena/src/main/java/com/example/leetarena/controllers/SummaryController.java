package com.example.leetarena.controllers;

import com.example.leetarena.models.Summary;
import com.example.leetarena.dtos.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.leetarena.services.SummaryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/summary")
public class SummaryController {

    @Autowired
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping
    public ResponseEntity<List<Summary>> getAllSummarys(){
        return ResponseEntity.ok(summaryService.getAllSummarys());
    }

    @GetMapping("/{sumamry_id}")
    public ResponseEntity<Summary> getSummaryById(@PathVariable("sumamry_id") int sumamry_id){
        return ResponseEntity.ok(summaryService.getSummaryById(sumamry_id));
    }

    @PostMapping("/create")
    public ResponseEntity<Summary> createSummary(@RequestBody SummaryDTO dto){
        return ResponseEntity.ok(summaryService.create(dto));
    }

    @PatchMapping("/edit_des/{id}")
    public ResponseEntity<Summary> updateSummaryDescription(@PathVariable("id") Integer id, @RequestBody() SummaryDTO dto){
        return ResponseEntity.ok(summaryService.updateSummaryDescription(id, dto));
    }

    @DeleteMapping("/delete/{summary_id}")
    public void deleteSummary(@PathVariable("summary_id") int summary_id){
        summaryService.deleteSummary(summary_id);
    }

}
