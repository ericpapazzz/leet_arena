package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.Player;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}