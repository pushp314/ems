package com.example.ems.repository;

import com.example.ems.models.AttendanceModel;
import com.example.ems.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    List<AttendanceModel> findByEmployee(EmployeeModel employee);
    List<AttendanceModel> findByDate(LocalDate date);
    List<AttendanceModel> findByEmployeeId(Long employeeId);
}
