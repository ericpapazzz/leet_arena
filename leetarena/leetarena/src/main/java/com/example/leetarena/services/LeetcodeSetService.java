package com.example.leetarena.services;

import com.example.leetarena.models.LeetcodeSet;
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

    public LeetcodeSet updateLeetcodeSet(LeetcodeSet leetcodeSet) {

        Optional<LeetcodeSet> leetcodeSet1 = leetcodeSetRepository.findById(leetcodeSet.getLeetcodeSetId());

        if(!leetcodeSet1.isPresent()) {
            throw new IllegalArgumentException("Could not find LeetcodeSet");
        }

        if(leetcodeSet.getProblems() == null || leetcodeSet.getProblems().isEmpty()) {
            throw new IllegalArgumentException("Problems were not provided");
        }

        LeetcodeSet updatedLeetcodeSet = leetcodeSet1.get();

        if(!updatedLeetcodeSet.getProblems().equals(leetcodeSet.getProblems())) {
            updatedLeetcodeSet.setProblems(leetcodeSet.getProblems());
        }

        return leetcodeSetRepository.save(updatedLeetcodeSet);
    }

    public void deleteLeetcodeSet(int leetcodeSetId) {
        if(!leetcodeSetRepository.existsById(leetcodeSetId)) {
            throw new IllegalArgumentException("Could not find LeetcodeSet with id: " + leetcodeSetId);
        }

        leetcodeSetRepository.deleteById(leetcodeSetId);
    }
}
