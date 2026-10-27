package com.example.employee_management_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.Set;

public record EmployeeRequestDto(
        @NotBlank(message = "Le matricule est obligatoire")
        String employeeId,

        @NotBlank(message = "Le nom est obligatoire")
        String name,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        String email,

        @NotBlank(message = "Le numéro de téléphone est obligatoire")
        String phone,

        @NotBlank(message = "Le poste est obligatoire")
        String jobTitle,

        @NotNull(message = "La date d'embauche est obligatoire")
        LocalDate hireDate,

        @Positive(message = "Le salaire doit être supérieur à zéro")
        double salary,

        @NotNull(message = "L'ID du département est obligatoire")
        Long departmentId,

        Long managerId,

        @NotNull(message = "L'adresse est obligatoire")
        @Valid
        AddressDto address,

        Set<Long> projectIds
) {
}
