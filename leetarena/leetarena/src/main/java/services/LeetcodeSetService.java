package services;

import models.LeetcodeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.LeetcodeSetRepository;

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

    public LeetcodeSet getLeetcodeSetById(int leetcode_set_id) {
        return leetcodeSetRepository.findById(leetcode_set_id).orElseThrow((
                () -> new RuntimeException("Could not find LeetcodeSet with id: " + leetcode_set_id)
                ));
    }

    public LeetcodeSet createLeetcodeSet(LeetcodeSet leetcodeSet) {

        if(leetcodeSet.getProblems() == null || leetcodeSet.getProblems().isEmpty()) {
            throw new IllegalArgumentException("Problems were not provided");
        }

        return leetcodeSetRepository.save(leetcodeSet);
    }

    public LeetcodeSet updateLeetcodeSet(LeetcodeSet leetcodeSet) {

        Optional<LeetcodeSet> leetcodeSet1 = leetcodeSetRepository.findById(leetcodeSet.getLeetcode_set_id());

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

    public void deleteLeetcodeSet(int leetcode_set_id) {
        if(!leetcodeSetRepository.existsById(leetcode_set_id)) {
            throw new IllegalArgumentException("Could not find LeetcodeSet with id: " + leetcode_set_id);
        }

        leetcodeSetRepository.deleteById(leetcode_set_id);
    }
}
