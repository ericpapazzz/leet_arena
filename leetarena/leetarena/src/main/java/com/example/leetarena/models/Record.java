package com.example.leetarena.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table( name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordId;

    @Column(name = "ranking")
    private String ranking;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User userId;

    /*
    @ManyToOne()
    @JoinColumn(name = "active_party_id)
    private Active_Party active_party;
     */
}
