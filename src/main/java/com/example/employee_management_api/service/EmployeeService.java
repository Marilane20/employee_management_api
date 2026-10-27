package com.example.employee_management_api.service;

import com.example.employee_management_api.dto.EmployeeRequestDto;
import com.example.employee_management_api.dto.EmployeeResponseDto;
import com.example.employee_management_api.entity.Adress;
import com.example.employee_management_api.entity.Department;
import com.example.employee_management_api.entity.Employee;
import com.example.employee_management_api.entity.Project;
import com.example.employee_management_api.exception.BusinessRuleException;
import com.example.employee_management_api.exception.DuplicateResourceException;
import com.example.employee_management_api.exception.ResourceNotFoundException;
import com.example.employee_management_api.mapper.EmployeeMapper;
import com.example.employee_management_api.repository.AdressRepository;
import com.example.employee_management_api.repository.DepartmentRepository;
import com.example.employee_management_api.repository.EmployeeRepository;
import com.example.employee_management_api.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            ProjectRepository projectRepository,
            EmployeeMapper employeeMapper
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeRequestDto request) {

        if (employeeRepository.existsByEmployeeId(request.employeeId())) {
            throw new DuplicateResourceException("Le matricule " + request.employeeId() + " est déjà utilisé");
        }
        if (employeeRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("L'adresse email " + request.email() + " est déjà utilisée");
        }
        if (employeeRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Le numéro de téléphone " + request.phone() + " est déjà utilisé");
        }

        // Résolution des entités liées
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + request.departmentId()));

        Employee manager = null;
        if (request.managerId() != null) {
            manager = employeeRepository.findById(request.managerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager introuvable avec l'ID : " + request.managerId()));
        }

        Set<Project> projects = new HashSet<>();
        if (request.projectIds() != null && !request.projectIds().isEmpty()) {
            List<Project> foundProjects = projectRepository.findAllById(request.projectIds());
            projects.addAll(foundProjects);
        }

        //  Transformation via le mapper
        Employee employee = employeeMapper.toEntity(request, department, manager, projects);

        // 4. Persistance (l'adresse est persistée en cascade via CascadeType.ALL)
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponseDto(saved);
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable avec l'ID : " + id));
    }

    public Page<EmployeeResponseDto> getEmployees(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toResponseDto);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable avec l'ID : " + id));

        // Règle de cohérence : bloquer la suppression si l'employé manage d'autres salariés
        if (!employee.getSubordinates().isEmpty()) {
            throw new BusinessRuleException(
                    "Impossible de supprimer cet employé : il supervise actuellement "
                            + employee.getSubordinates().size() + " subordonné(s). Réassignez son équipe."
            );
        }

        employeeRepository.delete(employee);
    }
}






