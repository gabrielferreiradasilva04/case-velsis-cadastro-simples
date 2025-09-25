package com.backend.backend.util.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.*;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*Classe responsável por capturar e tratar as exceptions lançadas pelos métodos durante a execução*/

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();
        List<ErrorDTO> errorsDtoList = errors
                .stream()
                .map(error -> new ErrorDTO(error.getField(), error.getDefaultMessage()))
                .toList();
        return new ResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Verifique os campos obrigatórios",
                errorsDtoList
        );

    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDTO handleEntityNotFoundException (EntityNotFoundException e) {
        return new ResponseErrorDTO(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null);
    }
}
