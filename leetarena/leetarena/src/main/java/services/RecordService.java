package services;

import models.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.RecordRepository;

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

    public Record getRecordById(int record_id){
        return recordRepository.findById(record_id).get();
    }

    public List<Record> getRecordsByUser(int user_id){
       return recordRepository.getRecordsByUserId(user_id);
    }

    //Post Methods

    public void createRecord(Record record){
        Record newRecord = new Record();
        newRecord.setRecords_id(newRecord.getRecords_id());

        if(record.getRanking() == null || record.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(record.getEnd_time() == null){
            throw new IllegalArgumentException("End_time is empty");
        }

        newRecord.setRanking(record.getRanking());
        newRecord.setEnd_time(record.getEnd_time());
        newRecord.setUser(record.getUser());
        recordRepository.save(newRecord);
    }


    // Put or Patch methods

    public void updateRecord(int record_id,Record record){
        Optional<Record> optionalRecord = recordRepository.findById(record_id);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("Record not found");
        }

        Record updatedRecord = optionalRecord.get();

        if(updatedRecord.getRanking() == null || updatedRecord.getRanking().isEmpty()){
            throw new IllegalArgumentException("Ranking is empty");
        }

        if(updatedRecord.getEnd_time() == null){
            throw new IllegalArgumentException("End_time is empty");
        }
        updatedRecord.setRanking(record.getRanking());
        updatedRecord.setEnd_time(record.getEnd_time());
        updatedRecord.setUser(record.getUser());

        recordRepository.save(updatedRecord);
    }

    //Delete methods
    public void deleteRecord(int record_id){
        Optional<Record> optionalRecord = recordRepository.findById(record_id);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("User not Foudned");
        }

        recordRepository.delete(optionalRecord.get());
    }




}
