package dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordDTO {
    private String ranking;
    private LocalDateTime end_time;
}
