package com.example.employee_management_api.service;

import com.example.employee_management_api.entity.Adress;
import com.example.employee_management_api.entity.Department;
import com.example.employee_management_api.entity.Employee;
import com.example.employee_management_api.entity.Project;
import com.example.employee_management_api.repository.AdressRepository;
import com.example.employee_management_api.repository.DepartmentRepository;
import com.example.employee_management_api.repository.EmployeeRepository;
import com.example.employee_management_api.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final AdressRepository adressRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeRepository;

    public EmployeeService(AdressRepository adressRepository, DepartmentRepository departmentRepository, ProjectRepository projectRepository ,EmployeeRepository employeRepository) {
        this.adressRepository = adressRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.employeRepository = employeRepository;
    }

    @Transactional
   public void onboardNewEmployee() {
       var adress = new Adress();
       adress.setStreet("etoudi abatoir");
       adress.setCity("yaounde");
       adress.setPostalcode("rue abbatoir");

        var savedAdress =adressRepository.save(adress);

       var project = new Project();
       project.setName("backend developpeur");

       var savedProject=projectRepository.save(project);

       var dept = new Department();
       dept.setName("engeneering");

       var sevedDepartment = departmentRepository.save(dept);

       var employe = new Employee();

       employe.setEmployeeId("22V2385");
       employe.setName("22V2385");
       employe.setEmail("marilanekamga@gmail.com");
       employe.setPhone("695728084");
       employe.setJobTitle("backend developper");
       employe.setHireDate(LocalDate.now());
       employe.setSalary(300000.0);
       employe.setDepartement(sevedDepartment);
       employe.setAddress(savedAdress);
       employe.getProjects().add(savedProject);

       employeRepository.save(employe);
   }

   public List<Employee>getAllEmployeeSorted(String sortBy,String direction) {
        final var sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();


        return employeRepository.findAll(sort);

    }

    public Page<Employee> getAllEmployees(int page,int size) {

        var pageable = PageRequest.of(page,size);
        return employeRepository.findAll(pageable);
    }

    public Page<Employee> getAllEmployeesPageAndSorted(int page,int size,String sortBy,String direction) {
        final var sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return employeRepository.findAll(pageable);
    }
   }






