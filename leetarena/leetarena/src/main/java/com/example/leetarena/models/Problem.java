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
    private Integer problemId;

    @Column(name = "problem_title")
    private String problemTitle;

    @Column(name = "problem_difficulty")
    private String difficulty;

    @Column(name = "problem_url")
    private String problemURL;

    @Column(name = "paid_only")
    private boolean paidOnly;

    @ManyToMany(mappedBy = "problemsList")
    private List<LeetcodeSet> leetcodeSets;


}
