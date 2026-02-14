package com.example.leetarena.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "random_id", nullable = false)
    private float random_id;

    @Column(name = "leetcode_id")
    private Integer leetcode_id;

    @ManyToMany(mappedBy = "problemsList")
    @JsonIgnore
    private List<LeetcodeSet> leetcodeSets;

    @PrePersist
    public void setRandom_id() {
        this.random_id = (float) Math.random();
    }

}
