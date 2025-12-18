package com.dragoncoredev.gestion_pedidos_api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // <-- Esto convierte a la clase en un "Interceptor Global"
public class GlobalExceptionHandler {

    /**
     * Este método salta automáticamente cuando alguien lanza un EntityNotFoundException
     * (por ejemplo, cuando no encontramos un producto o pedido).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(EntityNotFoundException ex) {

        // 1. Creamos nuestro JSON bonito con el mensaje del error original
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                LocalDateTime.now()
        );

        // 2. Devolvamos un error 404 (Not Found) en lugar de un 500
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}