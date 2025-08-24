package com.example.leetarena.repositories;

import com.example.leetarena.models.LeetcodeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeetcodeSetRepository extends JpaRepository<LeetcodeSet, Integer> {

}
