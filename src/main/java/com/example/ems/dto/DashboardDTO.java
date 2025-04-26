package com.example.ems.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalEmployees;
    private Long totalDepartments;
    private Long totalAttendanceRecords;
}
