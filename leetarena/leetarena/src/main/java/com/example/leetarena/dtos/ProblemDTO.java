package com.example.leetarena.dtos;

import lombok.Data;

@Data
public class ProblemDTO {

    private String title;

    private String difficulty;

    private String url;

    private boolean paidOnly;

}
