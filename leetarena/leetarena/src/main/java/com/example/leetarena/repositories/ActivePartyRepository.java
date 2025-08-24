package com.example.leetarena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leetarena.models.ActiveParty;

import java.util.Optional;

@Repository
public interface ActivePartyRepository extends JpaRepository<ActiveParty, Integer> {

}
