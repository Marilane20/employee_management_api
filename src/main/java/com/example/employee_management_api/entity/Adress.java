package com.example.employee_management_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adresses")

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


