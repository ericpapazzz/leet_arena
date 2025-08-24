package com.example.leetarena.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Leetcode_set")
public class LeetcodeSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leetcodeSet_id;

    @Column(columnDefinition = "TEXT")
    private String problems;


}
