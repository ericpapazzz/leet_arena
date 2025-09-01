package com.example.leetarena.services;

import com.example.leetarena.models.Problem;
import com.example.leetarena.repositories.ProblemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProblemService {

    //TODO: Implement a HashMap to store the values of the cantEasy,Medium and hard problems for the creation of the leetcodeSet

    private final ProblemRepository problemRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    //Initialize the problems with a fetch call

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void refreshProblems(){
        String url = "https://leetcode-api-pied.vercel.app/problems";
        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            List<Problem> problems = new ArrayList<>();

            Set<Integer> existingIds = problemRepository.findAll() //In this set we store the currentIds, so in the loop we dont have to iterate each object to find it, making to O(n) to O(1) search complxity
                    .stream()
                    .map(Problem :: getProblemId)
                    .collect(Collectors.toSet());

            if(root.isArray()){
                for(JsonNode node : root){
                    if(!existingIds.contains(node.get("id").asInt())){ //If there a new Problem we add it to the problems stack
                        Problem p = new Problem();
                        p.setProblemTitle(node.get("title").asText());
                        p.setProblemURL(node.get("url").asText());
                        p.setDifficulty(node.get("difficulty").asText());
                        p.setPaidOnly(node.get("paid_only").asBoolean());
                        problems.add(p);
                    }
                    else{
                        Problem pUpdate = problemRepository.findById(node.get("id").asInt()).orElseThrow(() -> new IllegalArgumentException("Problem not found"));

                        if(!pUpdate.getDifficulty().equals(node.get("difficulty").asText())){ //We update only if the difficulty or paid tags changed
                            pUpdate.setDifficulty(node.get("difficulty").asText());
                        }

                        if(!pUpdate.isPaidOnly() == node.get("paid_only").asBoolean()){
                            pUpdate.setPaidOnly(node.get("paid_only").asBoolean());
                        }

                        problemRepository.save(pUpdate);
                    }
                }
                problemRepository.saveAll(problems);
                System.out.println("Problems have been refreshed");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Get Methods
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public List<Problem> createLeetcodeSet(String difficulty, long endTimeDays){
        //TODO : Manage the logic of create LeetcodeSet based on difficulty and time

        int cantProblems = 0; //First we SetUp the cant of Problems that we need
        if(endTimeDays >= 1 && endTimeDays <= 5){
            cantProblems = 6;
        }
        else if(endTimeDays >= 6 && endTimeDays <= 14){
            cantProblems = 10;
        }
        else if(endTimeDays >= 15 && endTimeDays <= 21){
            cantProblems = 14;
        }
        else if(endTimeDays >= 22 && endTimeDays <= 31){
            cantProblems = 18;
        }

        //Now in this accordly to the cant Of Problems , we select the cant of each actegory, bedofre make the random search
        int cantEasy = 0;
        int cantMedium = 0;
        int cantHard = 0;

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
