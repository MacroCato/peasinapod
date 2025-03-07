package com.example.peasinapod.Data.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String surname;
    private String email;
    private String password;
}