package com.example.ems.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String employeeName;
    private String position;
    private String contact;
    private LocalDate hireDate;
    private String status;
    private Long departmentId;
}
