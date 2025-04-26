package com.example.ems.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String departmentName;
    private String description;
    private int employeeCount;
}
