package com.example.employee_management_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adresses")
@NoArgsConstructor
@AllArgsConstructor

public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street ;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalcode;

    @OneToOne(mappedBy = "address")
    private Employee employee;



}


