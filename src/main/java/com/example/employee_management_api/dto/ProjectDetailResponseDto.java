package com.example.employee_management_api.dto;

import java.util.List;

public record ProjectDetailResponseDto(

        Long id,
        String name,
        String description,
        int totalMembers,
        List<EmployeeSummaryDto> teamMembers

) {
}
