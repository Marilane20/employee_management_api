package com.example.employee_management_api.repository;

import com.example.employee_management_api.entity.Department;
import com.example.employee_management_api.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee ,Long> {

    @Query("SELECT e from Employee e WHERE e.departement.name =:department")
    List<Employee>findAllByDepartment(String department);

    @NativeQuery("SELECT e.* from employees e join adresses a on e.adresse_id = a.id where a.city =:city")
    List<Employee>findAllByCity(String city);

    List<Employee> findAllBySalaryGreaterThan(double salary);

    Page<Employee> findAll(Pageable pageable);

    boolean existsByEmployeeId(String employeeId);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);



}
