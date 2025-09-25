package com.backend.backend.util.exception;

import java.util.List;

public record ResponseErrorDTO(int status, String message, List<ErrorDTO> errors) {
}
