package controllers;

import models.LeetcodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.LeetcodeSetService;
import services.SummaryService;

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
    private ResponseEntity<LeetcodeSet> getLeetcodeSetById(@PathVariable("leetcode_set_id") int leetcode_set_id) {
        return ResponseEntity.ok(leetcodeSetService.getLeetcodeSetById(leetcode_set_id));
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
    private void deleteLeetcodeSet(@PathVariable("leetcode_set_id") int leetcode_set_id) {
        leetcodeSetService.deleteLeetcodeSet(leetcode_set_id);
    }
}
