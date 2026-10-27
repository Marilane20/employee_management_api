package com.example.employee_management_api.controller;
import com.example.employee_management_api.dto.DepartementDetailResponseDto;
import com.example.employee_management_api.dto.DepartementRequestDto;
import com.example.employee_management_api.dto.DepartmentSummaryDto;
import com.example.employee_management_api.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentSummaryDto> createDepartment(@Valid @RequestBody DepartementRequestDto request) {
        DepartmentSummaryDto created = departmentService.createDepartment(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentSummaryDto>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(departmentService.getAllDepartments(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementDetailResponseDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
