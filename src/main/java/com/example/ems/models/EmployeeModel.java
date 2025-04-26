package com.example.ems.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeName;
    private String position;
    private String contact;
    private LocalDate hireDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;
}
