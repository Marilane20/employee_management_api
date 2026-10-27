package com.example.employee_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartementRequestDto(

        @NotBlank(message = "Le nom du département est obligatoire")
        @Size(min = 2, max = 100, message = "Le nom doit comporter entre 2 et 100 caractères")
        String name

) {
}
