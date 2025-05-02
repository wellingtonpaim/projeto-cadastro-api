package br.univesp.pi.exception;

import lombok.Getter;

@Getter
public class ApiIllegalArgumentException extends RuntimeException {
    private final String entity;
    private final String field;
    private final Object value;

    public ApiIllegalArgumentException(String message, String entity, String field, Object value) {
        super(message);
        this.entity = entity;
        this.field = field;
        this.value = value;
    }
}