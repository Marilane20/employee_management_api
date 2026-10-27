package com.example.employee_management_api.controller;


import com.example.employee_management_api.entity.Employee;
import com.example.employee_management_api.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")

public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/onboarding")
    public ResponseEntity<Void>onboardEmployee(){
        employeeService.onboardNewEmployee();

        return ResponseEntity.ok().build();

    }

    @GetMapping
    public List<Employee>getAllEmployedSorted(
            @RequestParam( defaultValue = "name" ,required = false) String sortBy,
            @RequestParam( defaultValue = "asc", required = false) String direction
    )
    {
        return employeeService.getAllEmployeeSorted(sortBy ,direction);

    }

    @GetMapping("/paginate")
    public Page<Employee>getAllEmployees(
            @RequestParam (defaultValue = "0", required = false) int page,
            @RequestParam (defaultValue = "5", required = false) int size)
    {
    return employeeService.getAllEmployees(page,size);
    }

    @GetMapping("/paginateAndSort")
    public Page<Employee>getAllEmployeesPageAndSorted(
            @RequestParam (defaultValue = "0", required = false) int page,
            @RequestParam (defaultValue = "5", required = false) int size,
            @RequestParam( defaultValue = "name" ,required = false) String sortBy,
            @RequestParam( defaultValue = "asc", required = false) String direction) {
        return employeeService.getAllEmployeesPageAndSorted(page, size, sortBy, direction);

    }

}
