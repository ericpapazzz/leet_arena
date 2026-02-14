package com.example.leetarena.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer player_id;

    @Column(name = "player_username")
    private String playerUsername;

    @Column(name = "player_easys")
    private byte playerEasys;

    @Column(name = "player_mediums")
    private byte playerMediums;

    @Column(name = "player_hards")
    private byte playerHards;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
    name = "player_party",
    joinColumns = @JoinColumn(name = "player_id"),
    inverseJoinColumns = @JoinColumn(name = "party_id")
)
private List<Party> parties;

}
