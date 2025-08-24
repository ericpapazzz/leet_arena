package com.example.leetarena.controllers;

import com.example.leetarena.models.LeetcodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.leetarena.services.LeetcodeSetService;

import java.util.List;

@RestController
@RequestMapping("api/v1/leetcode_set")
public class LeetcodeSetController {

    @Autowired
    private final LeetcodeSetService leetcodeSetService;
    public LeetcodeSetController(LeetcodeSetService leetcodeSetService) {
        this.leetcodeSetService = leetcodeSetService;
    }

    @GetMapping
    private ResponseEntity<List<LeetcodeSet>> getLeetcodeSets() {
        return ResponseEntity.ok(leetcodeSetService.getAllLeetcodeSets());
    }

    @GetMapping("/{leetcode_set_id}")
    private ResponseEntity<LeetcodeSet> getLeetcodeSetById(@PathVariable("leetcode_set_id") int leetcodeSetId) {
        return ResponseEntity.ok(leetcodeSetService.getLeetcodeSetById(leetcodeSetId));
    }

    @PostMapping("/create")
    private ResponseEntity<LeetcodeSet> createLeetcodeSet(@RequestBody LeetcodeSet leetcodeSet) {
        return ResponseEntity.ok(leetcodeSetService.createLeetcodeSet(leetcodeSet));
    }

    @PutMapping("/update")
    private ResponseEntity<LeetcodeSet> updateLeetcodeSet(@RequestBody LeetcodeSet leetcodeSet) {
        return ResponseEntity.ok(leetcodeSetService.updateLeetcodeSet(leetcodeSet));
    }

    @DeleteMapping("/delete/{leetcode_set_id}")
    private void deleteLeetcodeSet(@PathVariable("leetcode_set_id") int leetcodeSetId) {
        leetcodeSetService.deleteLeetcodeSet(leetcodeSetId);
    }
}
