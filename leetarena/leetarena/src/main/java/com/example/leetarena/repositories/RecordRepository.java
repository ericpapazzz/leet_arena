package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    @Query(value = "SELECT r FROM Record r WHERE r.user.user_id = :user_id")
    List<Record> getRecordsByUserId(@Param("userId") int userId);
}
