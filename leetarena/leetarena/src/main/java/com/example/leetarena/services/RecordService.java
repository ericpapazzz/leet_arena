package com.example.leetarena.services;

import org.springframework.stereotype.Service;
import com.example.leetarena.models.Record;
import com.example.leetarena.models.User;
import com.example.leetarena.dtos.RecordDTO;
import com.example.leetarena.repositories.RecordRepository;
import com.example.leetarena.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService{
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public RecordService(RecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    //Get Methods
    public List<Record> getAllRecords(){
        return recordRepository.findAll();
    }

    public Record getRecordById(Integer recordId){
        return recordRepository.findById(recordId).get();
    }

    public List<Record> getRecordsByUser(Integer userId){
       return recordRepository.getRecordsByUserId(userId);
    }

    //Post Methods

    public Record createRecord(RecordDTO dto){
        User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        if(dto.getRanking() == null || dto.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(dto.getEndTime() == null){
            throw new IllegalArgumentException("End time is empty");
        }

        Record newRecord = new Record();
        newRecord.setRanking(dto.getRanking());
        newRecord.setEndTime(dto.getEndTime());
        newRecord.setUser(user);

        return recordRepository.save(newRecord);
    }


    // Put or Patch methods

    public Record updateRecord(Integer recordId,RecordDTO dto){
        User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Record> optionalRecord = recordRepository.findById(recordId);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("Record not found");
        }

        Record updatedRecord = optionalRecord.get();

        if(updatedRecord.getRanking() == null || updatedRecord.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(updatedRecord.getEndTime() == null){
            throw new IllegalArgumentException("End time is empty");
        }
        updatedRecord.setRanking(dto.getRanking());
        updatedRecord.setEndTime(dto.getEndTime());
        updatedRecord.setUser(user);

        return recordRepository.save(updatedRecord);
    }

    //Delete methods
    public void deleteRecord(Integer id){
        Optional<Record> optionalRecord = recordRepository.findById(id);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("Record not Foudned");
        }

        recordRepository.deleteById(id);
    }
}
