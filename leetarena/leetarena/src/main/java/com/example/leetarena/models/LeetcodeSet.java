package com.example.leetarena.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "leetcodeSet")
public class LeetcodeSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leetcodeSet_id;

    @ManyToMany //Many to many relationship
    @JoinTable(
            name = "leetcodeSetProblems",
            joinColumns = @JoinColumn(name = "leetcodeSetId"),
            inverseJoinColumns = @JoinColumn(name =  "problemId")
    )
    private List<Problem> problemsList;


}
