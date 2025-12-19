package com.dragoncoredev.gestion_pedidos_api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError; // Import nuevo
import org.springframework.web.bind.MethodArgumentNotValidException; // Import nuevo
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap; // Import nuevo
import java.util.Map;     // Import nuevo

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- MÉTODO 1: Para cuando no encontramos algo (404) ---
    // (Este es el que ya tenías)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(EntityNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // --- MÉTODO 2: Para cuando los datos no son válidos (400) ---
    // (Este es el NUEVO que añadimos ahora debajo)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Sacamos los errores de la lista y los metemos en nuestro mapa
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(nombreCampo, mensaje);
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}