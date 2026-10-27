package com.example.employee_management_api.controller;

import com.example.employee_management_api.dto.ProjectDetailResponseDto;
import com.example.employee_management_api.dto.ProjectRequestDto;
import com.example.employee_management_api.dto.ProjectSummaryDto;
import com.example.employee_management_api.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectSummaryDto> createProject(@Valid @RequestBody ProjectRequestDto request) {
        ProjectSummaryDto created = projectService.createProject(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectSummaryDto>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(projectService.getAllProjects(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailResponseDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<Void> assignEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId
    ) {
        projectService.assignEmployeeToProject(projectId, employeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
