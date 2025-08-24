package com.example.leetarena.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "active_partys")
public class ActiveParty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer active_party_id;

    @Column(name = "party_difficulty")
    private String party_difficulty;

    @Column(name = "party_prize")
    private String party_prize;

    @Column(name = "party_players_list")
    private List<String> party_players_list;

    @Column(name = "end_time")
    private LocalDateTime end_time;

    // @OneToOne
    // private Integer leetcode_set_id

    // @OneToOne
    // private Integer user_id;

}
