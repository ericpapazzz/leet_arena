package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import services.PlayerService;
import models.Player;
import dtos.PlayerDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:8080")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {
        // if player is not found, return 404
        try {
            return ResponseEntity.ok(playerService.getPlayerById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO dto) {
        try {
            return ResponseEntity.ok(playerService.createPlayer(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Integer id, @RequestBody PlayerDTO dto) {
        try {
            return ResponseEntity.ok(playerService.updatePlayer(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
