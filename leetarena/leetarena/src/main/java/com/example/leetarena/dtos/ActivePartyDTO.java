package com.example.leetarena.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivePartyDTO {
    
    private String difficulty;
    private String partyPrize;
    private LocalDateTime endTime;
}
