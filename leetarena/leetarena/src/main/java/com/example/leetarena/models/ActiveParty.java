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
    private String partyDifficulty;

    @Column(name = "party_prize")
    private String partyPrize;

    @Column(name = "party_players_list")
    private List<String> partyPlayersList;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "activeParty")
    private List<Record> records;

    // @OneToOne
    // private Integer leetcode_set_id

    // @OneToOne
    // private Integer user_id;

}
