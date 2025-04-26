package com.example.ems.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String employeeId;
    private String password;
}
