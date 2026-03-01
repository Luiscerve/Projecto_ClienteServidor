package com.luistienda.exception;

/**
 * Excepción para cuando no se encuentra un recurso.
 * Se usa cuando se busca una categoría o producto que no existe.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
