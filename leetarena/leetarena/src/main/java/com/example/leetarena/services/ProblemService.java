package com.example.leetarena.services;

import com.example.leetarena.models.Problem;
import com.example.leetarena.repositories.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Autowired
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    //Get Methods
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public List<Problem> createLeetcodeSet(){
        //TODO : Manage the logic of create LeetcodeSet based on difficulty
        return null;
    }

    //Post Methods

    public Problem addNewProblem(Problem problem) {
        Problem newProblem = new Problem();

        if (problem.getProblemTitle() == null || problem.getProblemTitle().isEmpty()) {
            throw new IllegalArgumentException("Problem title cannot be empty");
        }

        if (problem.getDifficulty() == null || problem.getDifficulty().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }

        if (problem.getProblemURL() == null || problem.getProblemURL().isEmpty()) {
            throw new IllegalArgumentException("Problem URL cannot be empty");
        }

        if (!problem.getProblemURL().contains("https://leetcode.com/problems/")) {
            throw new IllegalArgumentException("Problem URL must contain valid URL: https://leetcode.com/problems/");
        }

        newProblem.setProblemTitle(problem.getProblemTitle());
        newProblem.setProblemURL(problem.getProblemURL());
        newProblem.setDifficulty(problem.getDifficulty());
        return problemRepository.save(newProblem);
    }

    //Put Methods

    public Problem updateProblem(int problemId,Problem problem) {
        Optional<Problem> problemOptional = problemRepository.findById(problemId);

        if (!problemOptional.isPresent()) {
            throw new IllegalArgumentException("Problem not found");
        }

        Problem p1 = problemOptional.get();

        if (!p1.getProblemTitle().equals(problem.getProblemTitle())) {
            p1.setProblemTitle(problem.getProblemTitle());
        }

        if(!p1.getDifficulty().equals(problem.getDifficulty())){
            p1.setDifficulty(problem.getDifficulty());
        }

        if(!p1.getProblemURL().equals(problem.getProblemURL())){
            p1.setProblemURL(problem.getProblemURL());
        }

        return problemRepository.save(p1);
    }

    //Delete Methods
    public void deleteProblem(int problemId) {
        Optional<Problem> problemOptional = problemRepository.findById(problemId);
        if (!problemOptional.isPresent()) {
            throw new IllegalArgumentException("Problem not found");
        }

        problemRepository.delete(problemOptional.get());
    }



}
