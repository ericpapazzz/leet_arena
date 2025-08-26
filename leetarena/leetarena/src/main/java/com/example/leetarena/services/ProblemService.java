package com.example.leetarena.services;

import com.example.leetarena.models.Problem;
import com.example.leetarena.dtos.ProblemDTO;
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

    public Problem addNewProblem(ProblemDTO dto) {
        Problem newProblem = new Problem();

        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Problem title cannot be empty");
        }

        if (dto.getDifficulty() == null || dto.getDifficulty().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }

        if (dto.getUrl() == null || dto.getUrl().isEmpty()) {
            throw new IllegalArgumentException("Problem URL cannot be empty");
        }

        if (!dto.getUrl().contains("https://leetcode.com/problems/")) {
            throw new IllegalArgumentException("Problem URL must contain valid URL: https://leetcode.com/problems/");
        }

        newProblem.setProblemTitle(dto.getTitle());
        newProblem.setProblemURL(dto.getUrl());
        newProblem.setDifficulty(dto.getDifficulty());
        return problemRepository.save(newProblem);
    }

    //Put Methods

    public Problem updateProblem(Integer id,ProblemDTO dto) {
        Optional<Problem> problemOptional = problemRepository.findById(id);

        if (!problemOptional.isPresent()) {
            throw new IllegalArgumentException("Problem not found");
        }

        Problem p1 = problemOptional.get();

        if (!p1.getProblemTitle().equals(dto.getTitle())) {
            p1.setProblemTitle(dto.getTitle());
        }

        if(!p1.getDifficulty().equals(dto.getDifficulty())){
            p1.setDifficulty(dto.getDifficulty());
        }

        if(!p1.getProblemURL().equals(dto.getUrl())){
            p1.setProblemURL(dto.getUrl());
        }

        return problemRepository.save(p1);
    }

    //Delete Methods
    public void deleteProblem(Integer id) {
        Optional<Problem> problemOptional = problemRepository.findById(id);
        if (!problemOptional.isPresent()) {
            throw new IllegalArgumentException("Problem not found");
        }

        problemRepository.deleteById(id);
    }



}
