package com.example.employee_management_api.dto;

import java.util.List;

public record DepartementDetailResponseDto(

        Long id,
        String name,
        int totalEmployees,
        List<EmployeeSummaryDto> employees
) {
}
