package com.example.leetarena.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.leetarena.dtos.PartyDTO;
import com.example.leetarena.models.Party;
import com.example.leetarena.services.PartyService;


@RestController
@RequestMapping("/partys")
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
    public ResponseEntity<Party> createPartyDefault(@RequestParam Integer admin_id) {
        try {
            return ResponseEntity.ok(partyService.createPartyDefault(admin_id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/completeParty")
    public ResponseEntity<Party> completeParty(@RequestBody PartyDTO dto) {
        try{
            return ResponseEntity.ok(partyService.completeParty(dto));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(null);
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