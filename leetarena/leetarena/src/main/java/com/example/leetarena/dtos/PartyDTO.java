package com.example.leetarena.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.example.leetarena.models.Player;
import lombok.Data;

@Data
public class PartyDTO {
    private Integer party_id;
    private String difficulty;
    private String partyPrize;
    private LocalDateTime endTime;
    //private List<PlayerDTO> players; With the invitation code this is not necesary anymore
}
