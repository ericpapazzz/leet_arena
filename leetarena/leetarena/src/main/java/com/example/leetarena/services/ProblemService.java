package com.example.leetarena.services;

import com.example.leetarena.dtos.ProblemDTO;
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
import java.util.*;
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
                    .map(Problem :: getLeetcode_id)
                    .collect(Collectors.toSet());

            if(root.isArray()){
                for(JsonNode node : root){
                    if(!existingIds.contains(node.get("id").asInt())){ //If there a new Problem we add it to the problems stac
                        if(!node.get("paid_only").asBoolean()){ //If the problem is paid we skip it
                            Problem p = new Problem();
                            p.setProblemTitle(node.get("title").asText());
                            p.setProblemURL(node.get("url").asText());
                            p.setDifficulty(node.get("difficulty").asText());
                            p.setPaidOnly(node.get("paid_only").asBoolean());
                            p.setLeetcode_id(node.get("id").asInt());
                            problems.add(p);
                        }
                    }
                    else{
                        Problem pUpdate = problemRepository.findByLeetcode_id(node.get("id").asInt()).orElseThrow(() -> new IllegalArgumentException("Problem not found"));

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

    public List<Problem> createLeetcodeSet(String difficulty, long endTimeDays) {

        int cantProblems = 0; //First we SetUp the cant of Problems that we need
        if (endTimeDays >= 1 && endTimeDays <= 5) {
            cantProblems = 6;
        } else if (endTimeDays >= 6 && endTimeDays <= 14) {
            cantProblems = 10;
        } else if (endTimeDays >= 15 && endTimeDays <= 21) {
            cantProblems = 14;
        } else if (endTimeDays >= 22 && endTimeDays <= 31) {
            cantProblems = 18;
        }

        //Now in this accordly to the cant Of Problems , we select the cant of each category, before make the random search
        int cantEasy = 0;
        int cantMedium = 0;
        int cantHard = 0;

        //Easy Mode
        Map<String, Map<Integer, List<Integer>>> mode = new HashMap<>();

        //Problems structure and cant
        Map<Integer,List<Integer>> problemsSetEasy = new HashMap<>();

        //Make the set of the cant of problems by each cantDays
        List<Integer> cantProblemsList1 = List.of(3,3,0);
        List<Integer> cantProblemsList2 = List.of(5,4,1);
        List<Integer> cantProblemsList3 = List.of(6,7,1);
        List<Integer> cantProblemsList4 = List.of(8,9,1);

        //Add all the posible combinations in the map
        problemsSetEasy.put(1,cantProblemsList1);
        problemsSetEasy.put(2,cantProblemsList2);
        problemsSetEasy.put(3,cantProblemsList3);
        problemsSetEasy.put(4,cantProblemsList4);

        mode.put("easy",problemsSetEasy);

        //Medium Mode
        Map<Integer,List<Integer>> problemsSetMedium = new HashMap<>();
        List<Integer> cantProblemsList5 = List.of(2,4,0);
        List<Integer> cantProblemsList6 = List.of(4,5,1);
        List<Integer> cantProblemsList7 = List.of(4,8,2);
        List<Integer> cantProblemsList8 = List.of(5,11,2);

        problemsSetMedium.put(1,cantProblemsList5);
        problemsSetMedium.put(2,cantProblemsList6);
        problemsSetMedium.put(3,cantProblemsList7);
        problemsSetMedium.put(4,cantProblemsList8);

        mode.put("medium", problemsSetMedium);

        //Hard Mode
        Map<Integer,List<Integer>> problemsSetHard = new HashMap<>();
        List<Integer> cantProblemsList9 = List.of(2,3,1);
        List<Integer> cantProblemsList10 = List.of(2,6,2);
        List<Integer> cantProblemsList11 = List.of(2,9,3);
        List<Integer> cantProblemsList12 = List.of(2,12,4);

        problemsSetHard.put(1,cantProblemsList9);
        problemsSetHard.put(2,cantProblemsList10);
        problemsSetHard.put(3,cantProblemsList11);
        problemsSetHard.put(4,cantProblemsList12);

        mode.put("hard", problemsSetHard);

        //Now we can select the cant for each category based on the hashmap conditions and the difficulty and timedays

        //Check if the difficulty is a valid option
        Set<String> options = new HashSet<>(Arrays.asList("easy","medium","hard"));

        if(!options.contains(difficulty)){
            throw new IllegalArgumentException("Difficulty must be easy, medium or hard");
        }

        //Define the mode
        Map<Integer,List<Integer>> matchMode = mode.get(difficulty);

        List<Integer> tempCants = null;
        if(cantProblems == 6){
            tempCants = matchMode.get(1);
        }
        else if(cantProblems == 10){
            tempCants = matchMode.get(2);
        }
        else if(cantProblems == 14){
            tempCants = matchMode.get(3);
        }
        else if(cantProblems == 18){
            tempCants = matchMode.get(4);
        }
        else{
            throw new IllegalArgumentException("There is an invalid cantProblems, select a different time duration");
        }

        cantEasy = tempCants.get(0);
        cantMedium = tempCants.get(1);
        cantHard = tempCants.get(2);

        //Now we can make the random funtion to get the problems from the database

        List<Problem> listProblemsSet = new ArrayList<>();

        float r1 = (float) Math.random();
        float r2 = (float) Math.random();
        float r3 = (float) Math.random();

        List<Problem> easyOnes = problemRepository.getProblemsByDifficulty("easy",cantEasy,r1);

        List<Problem> mediumOnes = problemRepository.getProblemsByDifficulty("medium", cantMedium,r2);

        List<Problem> hardOnes = problemRepository.getProblemsByDifficulty("hard", cantHard,r3);

        listProblemsSet.addAll(easyOnes);
        listProblemsSet.addAll(mediumOnes);
        listProblemsSet.addAll(hardOnes);

        return listProblemsSet;
    }


    //Post Methods
    public Problem addNewProblem(ProblemDTO problem) {
        Problem newProblem = new Problem();

        if (problem.getTitle() == null || problem.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Problem title cannot be empty");
        }

        if (problem.getDifficulty() == null || problem.getDifficulty().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }

        if (problem.getUrl() == null || problem.getUrl().isEmpty()) {
            throw new IllegalArgumentException("Problem URL cannot be empty");
        }

        if (!problem.getUrl().contains("https://leetcode.com/problems/")) {
            throw new IllegalArgumentException("Problem URL must contain valid URL: https://leetcode.com/problems/");
        }

        newProblem.setProblemTitle(problem.getTitle());
        newProblem.setProblemURL(problem.getUrl());
        newProblem.setDifficulty(problem.getDifficulty());
        return problemRepository.save(newProblem);
    }

    //Put Methods

    public Problem updateProblem(int problemId,ProblemDTO problem) {
        Optional<Problem> problemOptional = problemRepository.findById(problemId);

        if (!problemOptional.isPresent()) {
            throw new IllegalArgumentException("Problem not found");
        }

        Problem p1 = problemOptional.get();

        if (!p1.getProblemTitle().equals(problem.getTitle())) {
            p1.setProblemTitle(problem.getTitle());
        }

        if(!p1.getDifficulty().equals(problem.getDifficulty())){
            p1.setDifficulty(problem.getDifficulty());
        }

        if(!p1.getProblemURL().equals(problem.getUrl())){
            p1.setProblemURL(problem.getUrl());
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
