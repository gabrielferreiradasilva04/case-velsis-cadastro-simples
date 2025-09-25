package com.backend.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name,  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate birthDate, String document, String addressLine,
                              String addressNumber, String city, String state, String zip,  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate createdAt,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")LocalDateTime updatedAt) {
}
