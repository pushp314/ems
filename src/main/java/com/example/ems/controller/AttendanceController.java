package com.example.ems.controller;

import com.example.ems.dto.AttendanceDTO;
import com.example.ems.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Only accessible by ADMIN
    public ResponseEntity<List<AttendanceDTO>> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')") // Accessible by both ADMIN and EMPLOYEE
    public ResponseEntity<List<AttendanceDTO>> getByEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(id));
    }

    @PostMapping("/clock-in/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or #employeeId == authentication.name") // Accessible by ADMIN or the specific employee
    public ResponseEntity<AttendanceDTO> clockIn(@PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.clockIn(employeeId));
    }

    @PutMapping("/clock-out/{attendanceId}")
    @PreAuthorize("hasRole('ADMIN') or #attendanceId == authentication.name") // Accessible by ADMIN or the specific employee
    public ResponseEntity<AttendanceDTO> clockOut(
            @PathVariable Long attendanceId,
            @RequestParam(required = false) String note) {
        return ResponseEntity.ok(attendanceService.clockOut(attendanceId, note));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')") // Only accessible by ADMIN
    public ResponseEntity<List<AttendanceDTO>> filterByDate(@RequestParam String date) {
        return ResponseEntity.ok(attendanceService.filterByDate(LocalDate.parse(date)));
    }
}
