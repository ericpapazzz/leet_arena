package com.example.leetarena.services;

import com.example.leetarena.models.LeetcodeSet;
import com.example.leetarena.dtos.LeetcodeSetDTO;
import com.example.leetarena.models.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.leetarena.repositories.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LeetcodeSetService {

    private final LeetcodeSetRepository leetcodeSetRepository;
    private final ProblemService problemService; //From here we select the problems for the

    @Autowired
    public LeetcodeSetService(LeetcodeSetRepository leetcodeSetRepository, ProblemService problemService) {
        this.leetcodeSetRepository = leetcodeSetRepository;
        this.problemService = problemService;
    }

    public List<LeetcodeSet> getAllLeetcodeSets() {
        return leetcodeSetRepository.findAll();
    }

    public LeetcodeSet getLeetcodeSetById(int leetcodeSetId) {
        return leetcodeSetRepository.findById(leetcodeSetId).orElseThrow((
                () -> new RuntimeException("Could not find LeetcodeSet with id: " + leetcodeSetId)
                ));
    }

    public LeetcodeSet createLeetcodeSet(String difficulty, LocalDateTime endTime) {
        long days = totalDays(endTime);

        if(days <= 0 || days > 31){ //If the time its not goot, we return a exception
            throw new RuntimeException("LeetcodeSet time duration has to be greater than 0 days or less than 31 days");
        }

        List<Problem> problemsSelected = problemService.createLeetcodeSet(difficulty, days);
        LeetcodeSet leetcodeSet = new LeetcodeSet();
        leetcodeSet.setProblemsList(problemsSelected);
        leetcodeSetRepository.save(leetcodeSet);
        return leetcodeSet;
    }

    //Extra method, NOT HTTP REQUEST OR ENDPOINT
    public long totalDays(LocalDateTime endTime){ //Method for calculate teh total time of match in days
        LocalDateTime current = LocalDateTime.now();

        if(!endTime.isBefore(current)){
            long diff = ChronoUnit.DAYS.between(current,endTime); //We use ChronoUnit to get the fullDays betwaen
            return (int) diff;
        }

        return -1; //We return -1 if the date was not provided well.
    }

    public LeetcodeSet updateLeetcodeSet(Integer id, LeetcodeSetDTO dto) {

        // Fetch the existing set
        LeetcodeSet existingSet = getLeetcodeSetById(id);
        if (existingSet == null) {
            throw new IllegalArgumentException("Could not find LeetcodeSet with id: " + id);
        }
    
        // Validate input
        if (dto.getProblems() == null || dto.getProblems().isEmpty()) {
            throw new IllegalArgumentException("Problems were not provided");
        }
    
        // Update fields
        if (!existingSet.getProblemsList().equals(dto.getProblems())) {
            existingSet.setProblemsList(dto.getProblems());
        }
    
        // Save and return
        return leetcodeSetRepository.save(existingSet);
    }    

    public void deleteLeetcodeSet(int leetcodeSetId) {
        if(!leetcodeSetRepository.existsById(leetcodeSetId)) {
            throw new IllegalArgumentException("Could not find LeetcodeSet with id: " + leetcodeSetId);
        }

        leetcodeSetRepository.deleteById(leetcodeSetId);
    }
}
