package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.Player;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query(value = "SELECT p FROM Player p WHERE p.user.user_id = :user_id")
    List<Player> getPlayersByUserId(@Param("user_id") int userId);
    
    @Modifying
    @Query(value = "DELETE FROM Player p WHERE p.user.user_id = :user_id")
    void deletePlayersByUserId(@Param("user_id") int userId);
}