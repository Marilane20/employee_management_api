package com.example.employee_management_api.repository;

import com.example.employee_management_api.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department , Long> {

    boolean existsByNameIgnoreCase(String name);
}


