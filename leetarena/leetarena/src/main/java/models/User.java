package models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "user_password_hash")
    private String user_password_hash;

    @Column(name = "user_leetcoins")
    private int user_leetcoins;

    // @ManyToOne(mappedBy = "user_id")
    // private List<Records> user_records;

}
