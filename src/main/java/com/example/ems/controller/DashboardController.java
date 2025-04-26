package com.example.ems.controller;

import com.example.ems.dto.DashboardDTO;
import com.example.ems.dto.EmployeeDashboardDTO;
import com.example.ems.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Only accessible by ADMIN
    public ResponseEntity<DashboardDTO> getAdminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminDashboardData());
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or #employeeId == authentication.name") // Accessible by ADMIN or the specific employee
    public ResponseEntity<EmployeeDashboardDTO> getEmployeeDashboard(@PathVariable Long employeeId) {
        return ResponseEntity.ok(dashboardService.getEmployeeDashboardData(employeeId));
    }
}
