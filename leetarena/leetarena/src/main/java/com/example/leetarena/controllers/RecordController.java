package com.example.leetarena.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.leetarena.models.Record;
import com.example.leetarena.dtos.RecordDTO;
import com.example.leetarena.services.RecordService;

import java.util.List;

@RestController
@RequestMapping("api/v1/record")
public class RecordController {

    @Autowired
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public ResponseEntity<List<Record>> getRecordAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(recordService.getRecordById(id));
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<Record>> getRecordsByUserId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(recordService.getRecordsByUser(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Record> addRecord(@RequestBody RecordDTO dto) {
        try{
            return ResponseEntity.ok(recordService.createRecord(dto));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecordById(@PathVariable Integer id) {
        try {
            recordService.deleteRecord(id);
            return ResponseEntity.noContent().build(); 
       } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
        
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Integer id,@RequestBody RecordDTO dto) {
        try{
            return ResponseEntity.ok(recordService.updateRecord(id,dto));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
