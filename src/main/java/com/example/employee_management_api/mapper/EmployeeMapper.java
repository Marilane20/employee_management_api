package com.example.employee_management_api.mapper;

import com.example.employee_management_api.dto.*;
import com.example.employee_management_api.entity.Adress;
import com.example.employee_management_api.entity.Department;
import com.example.employee_management_api.entity.Employee;
import com.example.employee_management_api.entity.Project;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {


    public Employee toEntity(EmployeeRequestDto dto, Department dept, Employee manager, Set<Project> projects) {
        if (dto == null) return null;

        Employee employee = new Employee();
        employee.setEmployeeId(dto.employeeId());
        employee.setName(dto.name());
        employee.setEmail(dto.email());
        employee.setPhone(dto.phone());
        employee.setJobTitle(dto.jobTitle());
        employee.setHireDate(dto.hireDate());
        employee.setSalary(dto.salary());

        // L'adresse est convertie et rattachée ici
        employee.setAddress(toAddressEntity(dto.address()));

        // Relations résolues par le service
        employee.setDepartement(dept);
        employee.setManager(manager);
        employee.setProjects(projects != null ? projects : Collections.emptySet());

        return employee;
    }

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
