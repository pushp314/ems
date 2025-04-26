package com.example.ems.service;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.models.DepartmentModel;
import com.example.ems.models.EmployeeModel;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeModel employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToDTO(employee);
    }

    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        EmployeeModel employee = new EmployeeModel();
        mapToEntity(dto, employee);
        EmployeeModel saved = employeeRepository.save(employee);
        return mapToDTO(saved);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        EmployeeModel employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        mapToEntity(dto, employee);
        return mapToDTO(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(EmployeeModel employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setPosition(employee.getPosition());
        dto.setContact(employee.getContact());
        dto.setHireDate(employee.getHireDate());
        dto.setStatus(employee.getStatus());
        dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        return dto;
    }

    private void mapToEntity(EmployeeDTO dto, EmployeeModel employee) {
        employee.setEmployeeName(dto.getEmployeeName());
        employee.setPosition(dto.getPosition());
        employee.setContact(dto.getContact());
        employee.setHireDate(dto.getHireDate());
        employee.setStatus(dto.getStatus());

        if (dto.getDepartmentId() != null) {
            DepartmentModel department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        } else {
            employee.setDepartment(null);
        }
    }
}
