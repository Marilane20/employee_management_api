package com.example.employee_management_api.dto;

import java.time.LocalDate;
import java.util.Set;

public record EmployeeResponseDto(

        Long id,
        String employeeId,
        String name,
        String email,
        String phone,
        String jobTitle,
        LocalDate hireDate,
        double salary,
        AddressDto address,
        DepartmentSummaryDto department,
        String managerName,
        Set<ProjectSummaryDto> projects
) {
}
