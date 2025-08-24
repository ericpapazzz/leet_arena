package com.example.leetarena.dtos;

import lombok.Data;
import com.example.leetarena.models.User;

import java.time.LocalDateTime;

@Data
public class RecordDTO {
    private String ranking;
    private LocalDateTime endTime;
    private User user;
}
