package com.example.leetarena.models;

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
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password_hash")
    private String userPasswordHash;

    @Column(name = "user_leetcoins")
    private int userLeetcoins;

    // @OneToMany(mappedBy = "user_id")
    // private List<Records> user_records;

}
