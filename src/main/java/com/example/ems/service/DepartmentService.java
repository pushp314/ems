package com.example.ems.service;

import com.example.ems.dto.DepartmentDTO;
import com.example.ems.models.DepartmentModel;
import com.example.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        DepartmentModel department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDTO(department);
    }

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        DepartmentModel department = new DepartmentModel();
        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        DepartmentModel saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        DepartmentModel department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        DepartmentModel updated = departmentRepository.save(department);
        return mapToDTO(updated);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO mapToDTO(DepartmentModel model) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(model.getId());
        dto.setDepartmentName(model.getDepartmentName());
        dto.setDescription(model.getDescription());
        dto.setEmployeeCount(model.getEmployeeCount());
        return dto;
    }
}
