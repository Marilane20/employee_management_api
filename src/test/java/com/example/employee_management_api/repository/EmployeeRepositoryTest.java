package com.example.employee_management_api.repository;


import com.example.employee_management_api.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testFindAll(){
        var employees =(List<Employee>) employeeRepository.findAll();

        assertEquals(10, employees.size());

    }

    @Test
    void testFindAllByDepartment(){

        var employee =(List<Employee>) employeeRepository.findAllByDepartment("Engineering");

        assertEquals(4,employee.size());

    }

    @Test
    void testFindAllByCity(){

            var employee =(List<Employee>) employeeRepository.findAllByCity("ebolowa");

            assertEquals(0,employee.size());
    }

    @Test
    void testFindAllBySalaryGreaterThan(){

        var employee =(List<Employee>) employeeRepository.findAllBySalaryGreaterThan(100000);

        assertEquals(2,employee.size());
    }

}