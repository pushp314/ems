package com.example.ems.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDashboardDTO {
    private String employeeName;
    private String departmentName;
    private String position;
    private Long totalAttendanceRecords;
}
