package com.example.leetarena.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leetarena.models.Record;
import com.example.leetarena.repositories.RecordRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService{
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    //Get Methods
    public List<Record> getAllRecords(){
        return recordRepository.findAll();
    }

    public Record getRecordById(int recordId){
        return recordRepository.findById(recordId).get();
    }

    public List<Record> getRecordsByUser(int userId){
       return recordRepository.getRecordsByUserId(userId);
    }

    //Post Methods

    public void createRecord(Record record){
        Record newRecord = new Record();
        newRecord.setRecordId(newRecord.getRecordId());

        if(record.getRanking() == null || record.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(record.getEndTime() == null){
            throw new IllegalArgumentException("End_time is empty");
        }

        newRecord.setRanking(record.getRanking());
        newRecord.setEndTime(record.getEndTime());
        newRecord.setUserId(record.getUserId());
        recordRepository.save(newRecord);
    }


    // Put or Patch methods

    public void updateRecord(int recordId,Record record){
        Optional<Record> optionalRecord = recordRepository.findById(recordId);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("Record not found");
        }

        Record updatedRecord = optionalRecord.get();

        if(updatedRecord.getRanking() == null || updatedRecord.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(updatedRecord.getEndTime() == null){
            throw new IllegalArgumentException("End_time is empty");
        }
        updatedRecord.setRanking(record.getRanking());
        updatedRecord.setEndTime(record.getEndTime());
        updatedRecord.setUserId(record.getUserId());

        recordRepository.save(updatedRecord);
    }

    //Delete methods
    public void deleteRecord(int recordId){
        Optional<Record> optionalRecord = recordRepository.findById(recordId);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("User not Found");
        }

        recordRepository.delete(optionalRecord.get());
    }




}
