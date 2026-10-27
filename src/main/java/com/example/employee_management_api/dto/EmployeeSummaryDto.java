package com.example.employee_management_api.dto;

public record EmployeeSummaryDto(

        Long id,
        String employeeId,
        String name,
        String jobTitle
) {
}
