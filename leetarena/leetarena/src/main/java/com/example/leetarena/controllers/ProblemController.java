package com.example.leetarena.controllers;

import com.example.leetarena.models.Problem;
import com.example.leetarena.services.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problem")
public class ProblemController {

    @Autowired
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping()
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/createLeetcodeSet")
    public List<Problem> createLeetcodeSet(){
       //TODO
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<Problem> addProblem(@RequestBody Problem problem) {
        return ResponseEntity.ok(problemService.addNewProblem(problem));
    }

    @PutMapping("/update/{problemId}")
    public ResponseEntity<Problem> updateProblem(@PathVariable ("problemId") int problemId,@RequestBody Problem problem) {
        return ResponseEntity.ok(problemService.updateProblem(problemId, problem));
    }

    @DeleteMapping("/delete/{problemId}")
    public void deleteProblem(@PathVariable("problemId") int problemId) {
        problemService.deleteProblem(problemId);
    }
}
