package com.example.ems.dto;

import com.example.ems.models.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private AttendanceStatus status;
    private String note;
    private Long employeeId;
    private String employeeName;
}
