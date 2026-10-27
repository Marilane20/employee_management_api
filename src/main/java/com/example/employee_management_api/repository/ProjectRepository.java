package com.example.employee_management_api.repository;

import com.example.employee_management_api.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project , Long> {

    boolean existsByNameIgnoreCase(String name);


}
