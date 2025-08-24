package models;

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
    private String summary_description;

    @OneToOne
    private ActiveParty active_party;

    /*
    @OneToMany
    private List<Player> podium;
     */


}
