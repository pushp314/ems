package com.example.ems.service;

import com.example.ems.dto.DashboardDTO;
import com.example.ems.dto.EmployeeDashboardDTO;
import com.example.ems.models.EmployeeModel;
import com.example.ems.repository.AttendanceRepository;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;

    public DashboardDTO getAdminDashboardData() {
        long employeeCount = employeeRepository.count();
        long departmentCount = departmentRepository.count();
        long attendanceCount = attendanceRepository.count();

        return new DashboardDTO(employeeCount, departmentCount, attendanceCount);
    }

    public EmployeeDashboardDTO getEmployeeDashboardData(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        long attendanceRecords = attendanceRepository.findByEmployeeId(employeeId).size();

        return new EmployeeDashboardDTO(
                employee.getEmployeeName(),
                employee.getDepartment().getDepartmentName(),
                employee.getPosition(),
                attendanceRecords
        );
    }
}
