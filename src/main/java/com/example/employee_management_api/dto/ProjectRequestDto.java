package com.example.employee_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequestDto(
        @NotBlank(message = "Le nom du projet est obligatoire")
        @Size(min = 2, max = 150, message = "Le nom du projet doit comporter entre 2 et 150 caractères")
        String name,

        @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
        String description
)
{
}
