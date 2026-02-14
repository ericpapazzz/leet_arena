package com.example.leetarena.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Data
@Table(name = "partys")
public class Party {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("party_id")
    private Integer party_id;

    @Column(name = "party_difficulty")
    private String partyDifficulty;

    @Column(name = "party_prize")
    private String partyPrize;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "party_status")
    private String party_status;

    @Column(name = "invitation_code", unique = true)
    private String invitation_code;

    @OneToMany(mappedBy = "party")
    private List<Record> records;

    @OneToOne
    @JoinColumn(name = "leetcode_set_id")
    private LeetcodeSet leetcodeSet;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "parties")
    private List<Player> players;

    @OneToOne
    private Summary summary;
}
