package com.example.leetarena.services;

import com.example.leetarena.models.Problem;
import com.example.leetarena.repositories.ProblemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        refreshProblems();
    }

    //Initialize the problems with a fetch call

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void refreshProblems(){
        String url = "https://leetcode-api-pied.vercel.app/problems";
        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response).get("stat_status_pair");

            List<Problem> problems = new ArrayList<>();

            for(JsonNode node : root){
                Problem p = new Problem();
                p.setProblemId(node.get("stat").get("id").asInt());
                p.setProblemTitle(node.get("stat").get("title").asText());
                p.setProblemURL(node.get("stat").get("url").asText());
                p.setDifficulty(node.get("stat").get("difficulty").asText());
                p.setPaidOnly(node.get("stat").get("paid_only").asBoolean());
                problems.add(p);
            }
            problemRepository.saveAll(problems);
            System.out.println("Problems have been refreshed");

        } catch (Exception e){
            e.printStackTrace();
        }
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
