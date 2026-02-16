package com.example.leetarena.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.leetarena.dtos.PartyDTO;
import com.example.leetarena.models.Party;
import com.example.leetarena.services.PartyService;


@RestController
@RequestMapping("/api/v1/partys")
@CrossOrigin(origins = "http://localhost:8080")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @GetMapping
    public ResponseEntity<List<Party>> getAllPartys() {
        return ResponseEntity.ok(partyService.getAllPartys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable Integer id) {
        // if party is not found, return 404
        try {
            return ResponseEntity.ok(partyService.getActivePartyById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPartyDefault(@RequestParam Integer admin_id) {
        try {
            return ResponseEntity.ok(partyService.createPartyDefault(admin_id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/completeParty")
    public ResponseEntity<?> completeParty(@RequestBody PartyDTO dto) {
        try{
            return ResponseEntity.ok(partyService.completeParty(dto));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/completeParty/newPlayer")
    public ResponseEntity<?> addNewPlayerToWaitingParty(@RequestParam Integer user_id, @RequestParam String invitation_code){
        try{
            return ResponseEntity.ok(partyService.addNewPlayerToWaitingParty(user_id, invitation_code));
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

   @PutMapping("/{id}")
   public ResponseEntity<Party> updateParty(@PathVariable Integer id, @RequestBody PartyDTO dto) {
    try {
        return ResponseEntity.ok(partyService.updateParty(id, dto));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(null);
    }
}

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
            try {
                partyService.deleteParty(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
    }

}