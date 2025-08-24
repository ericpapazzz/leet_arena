package com.example.leetarena.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "party_summarys")
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int summary_id;

    @Column(name = "summary_description")
    private String summaryDescription;

    @OneToOne
    private ActiveParty activeParty;

    /*
    @OneToMany
    private List<Player> podium;
     */


}
