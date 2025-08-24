package com.example.leetarena.controllers;

import com.example.leetarena.models.Summary;
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

    @GetMapping("/{summary_id}")
    public ResponseEntity<Summary> getSummaryById(@PathVariable("summary_id") int summaryId){
        return ResponseEntity.ok(summaryService.getSummaryById(summaryId));
    }

    @PostMapping("/create")
    public ResponseEntity<Summary> createSummary(@RequestBody Summary summary){
        return ResponseEntity.ok(summaryService.addSummary(summary));
    }

    @PatchMapping("/edit_des/{summary_id}")
    public ResponseEntity<Summary> updateSummaryDescription(@PathVariable("summary_id") int summaryId, @RequestParam() String description){
        return ResponseEntity.ok(summaryService.updateSummaryDescription(summaryId, description));
    }

    @DeleteMapping("/delete/{summary_id}")
    public void deleteSummary(@PathVariable("summary_id") int summaryId){
        summaryService.deleteSummary(summaryId);
    }

}
