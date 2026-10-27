package com.example.employee_management_api.mapper;

import com.example.employee_management_api.dto.DepartementDetailResponseDto;
import com.example.employee_management_api.dto.DepartementRequestDto;
import com.example.employee_management_api.dto.DepartmentSummaryDto;
import com.example.employee_management_api.dto.EmployeeSummaryDto;
import com.example.employee_management_api.entity.Department;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DepartmentMapper {

    // DTO -> Entity (pour la création)
    public Department toEntity(DepartementRequestDto dto) {
        if (dto == null) return null;
        Department department = new Department();
        department.setName(dto.name());
        return department;
    }

    // Entity -> Summary DTO (pour les listes paginées)
    public DepartmentSummaryDto toSummaryDto(Department entity) {
        if (entity == null) return null;
        return new DepartmentSummaryDto(entity.getId(), entity.getName());
    }

    // Entity -> Detail DTO (avec la liste des employés du département)
    public DepartementDetailResponseDto toDetailDto(Department entity) {
        if (entity == null) return null;

        List<EmployeeSummaryDto> employeeDtos = (entity.getEmployees() == null)
                ? Collections.emptyList()
                : entity.getEmployees().stream()
                .map(e -> new EmployeeSummaryDto(e.getId(), e.getEmployeeId(), e.getName(), e.getJobTitle()))
                .toList();

        return new DepartementDetailResponseDto(
                entity.getId(),
                entity.getName(),
                employeeDtos.size(),
                employeeDtos
        );
    }
}
