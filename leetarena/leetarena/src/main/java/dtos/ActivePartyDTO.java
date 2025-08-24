package dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivePartyDTO {
    
    private String difficulty;
    private String party_prize;
    private LocalDateTime end_time;
}
