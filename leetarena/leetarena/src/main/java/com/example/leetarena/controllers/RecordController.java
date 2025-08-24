package com.example.leetarena.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.leetarena.models.Record;
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

    @GetMapping("/{record_id}")
    public ResponseEntity<Record> getRecordById(@PathVariable("record_id") int record_id) {
        return ResponseEntity.ok(recordService.getRecordById(record_id));
    }

    @GetMapping("/byuser/{user_id}")
    public ResponseEntity<List<Record>> getRecordsByUserId(@PathVariable("user_id") int user_id) {
        return ResponseEntity.ok(recordService.getRecordsByUser(user_id));
    }

    @PostMapping("/create")
    public void addRecord(@RequestBody Record newRecord) {
        recordService.createRecord(newRecord);
    }

    @DeleteMapping("/delete/{record_id}")
    public void deleteRecordById(@PathVariable("record_id") int record_id) {
        recordService.deleteRecord(record_id);
    }

    @PutMapping("/update/{record_id}")
    public void updateRecord(@PathVariable("record_id") int record_id,@RequestBody Record updatedRecord) {
        recordService.updateRecord(record_id, updatedRecord);
    }

}
