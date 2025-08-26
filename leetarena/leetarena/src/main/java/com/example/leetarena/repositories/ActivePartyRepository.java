package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.ActiveParty;

import java.util.Optional;
import java.util.List;

@Repository
public interface ActivePartyRepository extends JpaRepository<ActiveParty, Integer> {

    @Query(value = "SELECT ap FROM ActiveParty ap WHERE ap.user.user_id = :user_id")
    List<ActiveParty> getActivePartiesByUserId(@Param("user_id") Integer userId);
    
    @Modifying
    @Query(value = "DELETE FROM ActiveParty ap WHERE ap.user.user_id = :user_id")
    void deleteActivePartiesByUserId(@Param("user_id") Integer userId);
}
