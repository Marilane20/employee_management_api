package com.example.employee_management_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(

    @NotBlank(message = "La rue est obligatoire")
    String street,

    @NotBlank(message = "La ville est obligatoire")
    String city,

    @NotBlank(message = "Le code postal est obligatoire")
    String postalcode ){
}
