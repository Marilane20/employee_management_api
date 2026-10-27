package com.example.employee_management_api.service;

import com.example.employee_management_api.dto.ProjectDetailResponseDto;
import com.example.employee_management_api.dto.ProjectRequestDto;
import com.example.employee_management_api.dto.ProjectSummaryDto;
import com.example.employee_management_api.entity.Employee;
import com.example.employee_management_api.entity.Project;
import com.example.employee_management_api.exception.DuplicateResourceException;
import com.example.employee_management_api.exception.ResourceNotFoundException;
import com.example.employee_management_api.mapper.ProjectMapper;
import com.example.employee_management_api.repository.EmployeeRepository;
import com.example.employee_management_api.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            ProjectMapper projectMapper
    ) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.projectMapper = projectMapper;
    }

    @Transactional
    public ProjectSummaryDto createProject(ProjectRequestDto request) {
        if (projectRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Un projet nommé '" + request.name() + "' existe déjà");
        }

        Project project = projectMapper.toEntity(request);
        Project saved = projectRepository.save(project);
        return projectMapper.toSummaryDto(saved);
    }

    public Page<ProjectSummaryDto> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return projectRepository.findAll(pageable)
                .map(projectMapper::toSummaryDto);
    }

    public ProjectDetailResponseDto getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec l'ID : " + id));
    }

    @Transactional
    public void assignEmployeeToProject(Long projectId, Long employeeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec l'ID : " + projectId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employé introuvable avec l'ID : " + employeeId));

        // Utilise la méthode d'assistance de synchronisation bidirectionnelle
        employee.addProject(project);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable avec l'ID : " + id));

        // Détacher le projet des employés avant de le supprimer physiquement
        for (Employee member : project.getEmployees()) {
            member.getProjects().remove(project);
        }

        projectRepository.delete(project);
    }
    }
