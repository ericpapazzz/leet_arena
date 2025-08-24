package com.example.leetarena.services;

import com.example.leetarena.models.LeetcodeSet;
import com.example.leetarena.dtos.LeetcodeSetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.leetarena.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class LeetcodeSetService {

    private final LeetcodeSetRepository leetcodeSetRepository;

    @Autowired
    public LeetcodeSetService(LeetcodeSetRepository leetcodeSetRepository) {
        this.leetcodeSetRepository = leetcodeSetRepository;
    }

    public List<LeetcodeSet> getAllLeetcodeSets() {
        return leetcodeSetRepository.findAll();
    }

    public LeetcodeSet getLeetcodeSetById(int leetcodeSetId) {
        return leetcodeSetRepository.findById(leetcodeSetId).orElseThrow((
                () -> new RuntimeException("Could not find LeetcodeSet with id: " + leetcodeSetId)
                ));
    }

    public LeetcodeSet createLeetcodeSet(LeetcodeSet leetcodeSet) {

        if(leetcodeSet.getProblems() == null || leetcodeSet.getProblems().isEmpty()) {
            throw new IllegalArgumentException("Problems were not provided");
        }

        return leetcodeSetRepository.save(leetcodeSet);
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
        if (!existingSet.getProblems().equals(dto.getProblems())) {
            existingSet.setProblems(dto.getProblems());
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
