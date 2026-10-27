package com.example.employee_management_api.repository;

import com.example.employee_management_api.entity.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdressRepository extends JpaRepository<Adress , Long> {
    List<Adress> findAllByCity(String city);
    Optional<Adress> findByStreetAndCity(String street, String city);

}
