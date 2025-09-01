package com.example.leetarena.controllers;

import com.example.leetarena.models.Problem;
import com.example.leetarena.dtos.ProblemDTO;
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

    @PostMapping("/add")
    public ResponseEntity<Problem> addProblem(@RequestBody ProblemDTO dto) {
        return ResponseEntity.ok(problemService.addNewProblem(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Problem> updateProblem(@PathVariable Integer id,@RequestBody ProblemDTO dto) {
        return ResponseEntity.ok(problemService.updateProblem(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProblem(@PathVariable Integer id) {
        problemService.deleteProblem(id);
    }
}