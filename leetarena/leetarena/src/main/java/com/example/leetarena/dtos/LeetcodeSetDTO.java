package com.example.leetarena.dtos;

import com.example.leetarena.models.Problem;
import lombok.Data;

import java.util.List;

@Data
public class LeetcodeSetDTO {
    private List<Problem> problems;
}
