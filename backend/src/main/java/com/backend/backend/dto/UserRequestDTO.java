package com.backend.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequestDTO(@NotBlank(message = "Nome não pode ser nulo") String name,
                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") @NotNull(message = "Data de nascimento não pode ser nula") LocalDate birthDate,
                             @NotBlank(message = "Documento não pode ser nulo") String document,
                             @NotBlank(message = "Linha de endereço não pode ser nula") String addressLine,
                             @Min(value = 1, message = "Número de endereço deve ser maior que zero") int addressNumber,
                             @NotBlank(message = "Cidade não pode ser nula")String city,
                             @NotBlank(message = "Estado não pode ser nulo") String state,
                             @NotBlank(message = "Codigo postal não pode ser nulo") String zip) {
}
