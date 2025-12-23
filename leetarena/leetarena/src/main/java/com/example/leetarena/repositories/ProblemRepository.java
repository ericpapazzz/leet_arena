package com.example.leetarena.repositories;

import com.example.leetarena.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

    @Query("SELECT p FROM Problem p WHERE p.leetcode_id = :leetcode_id")
    Optional<Problem> findByLeetcode_id(@Param("leetcode_id") Integer leetcode_id);

    @Query("SELECT p FROM Problem p WHERE p.difficulty = :difficulty AND p.random_id >= :random_id ORDER BY p.random_id LIMIT :cant_problems")
    List<Problem> getProblemsByDifficulty(@Param(value = "difficulty")String difficulty,@Param(value = "cant_problems") int cant_problems,@Param(value = "random_id") float random_id);
}
