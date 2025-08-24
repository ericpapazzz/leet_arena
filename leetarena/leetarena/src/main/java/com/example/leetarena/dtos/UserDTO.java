package com.example.leetarena.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String passwordHash;
}