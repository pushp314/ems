package com.example.ems.repository;

import com.example.ems.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmployeeId(String employeeId);
    boolean existsByEmployeeId(String employeeId); // For signup validation
}
