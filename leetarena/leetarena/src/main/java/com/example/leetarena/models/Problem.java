package com.example.leetarena.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId;

    @Column(name = "problemTitle")
    private String problemTitle;
    @Column(name = "problemDifficulty")
    private String difficulty;
    @Column(name = "problemURL")
    private String problemURL;
    @Column(name = "paidOnly")
    private boolean paidOnly;

    @ManyToMany(mappedBy = "problemsList")
    private List<LeetcodeSet> leetcodeSets;


}
