package com.example.employee_management_api.mapper;

import com.example.employee_management_api.dto.AddressDto;
import com.example.employee_management_api.dto.DepartmentSummaryDto;
import com.example.employee_management_api.dto.EmployeeResponseDto;
import com.example.employee_management_api.dto.ProjectSummaryDto;
import com.example.employee_management_api.entity.Adress;
import com.example.employee_management_api.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeResponseDto toResponseDto(Employee entity) {
        if (entity == null) return null;

        AddressDto addressDto = null;
        if (entity.getAddress() != null) {
            addressDto = new AddressDto(
                    entity.getAddress().getStreet(),
                    entity.getAddress().getCity(),
                    entity.getAddress().getPostalcode()
            );
        }

        DepartmentSummaryDto deptDto = null;
        if (entity.getDepartement() != null) {
            deptDto = new DepartmentSummaryDto(
                    entity.getDepartement().getId(),
                    entity.getDepartement().getName()
            );
        }

        String managerName = (entity.getManager() != null) ? entity.getManager().getName() : null;

        var projectsDto = (entity.getProjects() == null)
                ? Collections.<ProjectSummaryDto>emptySet()
                : entity.getProjects().stream()
                .map(p -> new ProjectSummaryDto(p.getId(), p.getName()))
                .collect(Collectors.toSet());

        return new EmployeeResponseDto(
                entity.getId(),
                entity.getEmployeeId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getJobTitle(),
                entity.getHireDate(),
                entity.getSalary(),
                addressDto,
                deptDto,
                managerName,
                projectsDto
        );
    }

    public Adress toAddressEntity(AddressDto dto) {
        if (dto == null) return null;
        Adress address = new Adress();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setPostalcode(dto.postalcode());
        return address;
    }
}
