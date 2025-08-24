package com.example.leetarena.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.leetarena.dtos.ActivePartyDTO;
import com.example.leetarena.models.ActiveParty;
import com.example.leetarena.services.ActivePartyService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/Partys")
@CrossOrigin(origins = "http://localhost:8080")
public class ActivePartyController {

    @Autowired
    private ActivePartyService activePartyService;

    @GetMapping
    public ResponseEntity<List<ActiveParty>> getAllPartys() {
        return ResponseEntity.ok(activePartyService.getAllPartys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiveParty> getPartyById(@PathVariable Integer id) {
        // if party is not found, return 404
        try {
            return ResponseEntity.ok(activePartyService.getActivePartyById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/create")
    public ResponseEntity<ActiveParty> createParty(@RequestBody ActivePartyDTO dto) {
        try {
            return ResponseEntity.ok(activePartyService.createParty(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

   @PutMapping("/{id}")
   public ResponseEntity<ActiveParty> updateParty(@PathVariable Integer id, @RequestBody ActivePartyDTO dto) {
    try {
        return ResponseEntity.ok(activePartyService.updateParty(id, dto));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(null);
    }
}

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
            try {
                activePartyService.deleteParty(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
    }

}