package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.Player;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}