package com.backend.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, LocalDate birthDate, String document, String addressLine,
                              String addressNumber, String city, String state, String zip, LocalDate createdAt,
                              LocalDateTime updatedAt) {
}
