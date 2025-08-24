package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer player_id;

    @Column(name = "player_username")
    private String player_username;

    @Column(name = "player_easys")
    private byte player_easys;

    @Column(name = "player_mediums")
    private byte player_mediums;

    @Column(name = "player_hards")
    private byte player_hards;

    @ManyToOne
    private Integer user_id;

}
