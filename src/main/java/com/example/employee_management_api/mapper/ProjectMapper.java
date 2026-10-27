package com.example.employee_management_api.mapper;

import com.example.employee_management_api.dto.EmployeeSummaryDto;
import com.example.employee_management_api.dto.ProjectDetailResponseDto;
import com.example.employee_management_api.dto.ProjectRequestDto;
import com.example.employee_management_api.dto.ProjectSummaryDto;
import com.example.employee_management_api.entity.Project;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProjectMapper {
     // DTO -> Entity (pour la création)
        public Project toEntity(ProjectRequestDto dto) {
            if (dto == null) return null;
            Project project = new Project();
            project.setName(dto.name());
            project.setDescription(dto.description());
            return project;
        }

        // Entity -> Summary DTO (pour les listes paginées)
        public ProjectSummaryDto toSummaryDto(Project entity) {
            if (entity == null) return null;
            return new ProjectSummaryDto(entity.getId(), entity.getName());
        }

        // Entity -> Detail DTO (avec l'équipe de projet)
        public ProjectDetailResponseDto toDetailDto(Project entity) {
            if (entity == null) return null;

            List<EmployeeSummaryDto> memberDtos = (entity.getEmployees() == null)
                    ? Collections.emptyList()
                    : entity.getEmployees().stream()
                    .map(e -> new EmployeeSummaryDto(e.getId(), e.getEmployeeId(), e.getName(), e.getJobTitle()))
                    .toList();

            return new ProjectDetailResponseDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    memberDtos.size(),
                    memberDtos
            );
        }
}
