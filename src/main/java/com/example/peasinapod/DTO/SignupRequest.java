package com.example.peasinapod.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String surname;
    private String email;
    private String password;
}