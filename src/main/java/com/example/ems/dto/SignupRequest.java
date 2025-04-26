package com.example.ems.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String employeeId;
    private String name;
    private String password;
    private String role; // Admin or Employee
}
