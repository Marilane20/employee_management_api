package com.example.employee_management_api.service;

import com.example.employee_management_api.dto.DepartementDetailResponseDto;
import com.example.employee_management_api.dto.DepartementRequestDto;
import com.example.employee_management_api.dto.DepartmentSummaryDto;
import com.example.employee_management_api.entity.Department;
import com.example.employee_management_api.exception.BusinessRuleException;
import com.example.employee_management_api.exception.DuplicateResourceException;
import com.example.employee_management_api.exception.ResourceNotFoundException;
import com.example.employee_management_api.mapper.DepartmentMapper;
import com.example.employee_management_api.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository,DepartmentMapper departmentMapper){
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;

    }

    @Transactional
    public DepartmentSummaryDto createDepartment(DepartementRequestDto request) {
        if (departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Un département nommé '" + request.name() + "' existe déjà");
        }

        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toSummaryDto(saved);
    }

    public Page<DepartmentSummaryDto> getAllDepartments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toSummaryDto);
    }

    public DepartementDetailResponseDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + id));
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + id));

        // Règle de protection d'entreprise : interdire la suppression si le département contient des salariés
        if (!dept.getEmployees().isEmpty()) {
            throw new BusinessRuleException(
                    "Suppression rejetée : Le département '" + dept.getName() + "' contient "
                            + dept.getEmployees().size() + " employé(s). Réaffectez-les au préalable."
            );
        }

        departmentRepository.delete(dept);
    }




}
