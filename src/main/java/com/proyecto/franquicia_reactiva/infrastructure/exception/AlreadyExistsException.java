package com.proyecto.franquicia_reactiva.infrastructure.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}