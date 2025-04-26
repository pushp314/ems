package com.example.ems.service;

import com.example.ems.dto.AttendanceDTO;
import com.example.ems.models.*;
import com.example.ems.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<AttendanceDTO> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceByEmployee(Long employeeId) {
        EmployeeModel emp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return attendanceRepository.findByEmployee(emp).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AttendanceDTO clockIn(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        AttendanceModel attendance = new AttendanceModel();
        attendance.setDate(LocalDate.now());
        attendance.setClockIn(LocalTime.now());
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setEmployee(employee);
        return mapToDTO(attendanceRepository.save(attendance));
    }

    public AttendanceDTO clockOut(Long attendanceId, String note) {
        AttendanceModel attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        attendance.setClockOut(LocalTime.now());
        if (note != null && !note.isEmpty()) {
            attendance.setNote(note);
        }
        return mapToDTO(attendanceRepository.save(attendance));
    }

    public List<AttendanceDTO> filterByDate(LocalDate date) {
        return attendanceRepository.findByDate(date).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AttendanceDTO mapToDTO(AttendanceModel model) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setClockIn(model.getClockIn());
        dto.setClockOut(model.getClockOut());
        dto.setStatus(model.getStatus());
        dto.setNote(model.getNote());
        dto.setEmployeeId(model.getEmployee().getId());
        dto.setEmployeeName(model.getEmployee().getEmployeeName());
        return dto;
    }
}
