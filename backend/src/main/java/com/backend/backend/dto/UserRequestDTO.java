package com.backend.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequestDTO(@NotNull(message = "Nome não pode ser nulo") String name,
                             @NotNull(message = "Data de nascimento não pode ser nula") LocalDate birthDate,
                             @NotNull(message = "Documento não pode ser nulo") String document,
                             @NotNull(message = "Linha de endereço não pode ser nula") String addressLine,
                             @Min(value = 1, message = "Número de endereço deve ser maior que zero") int addressNumber,
                             @NotNull(message = "Cidade não pode ser nula")String city,
                             @NotNull(message = "Estado não pode ser nulo") String state,
                             @NotNull(message = "Codigo postal não pode ser nulo") String zip) {
}
