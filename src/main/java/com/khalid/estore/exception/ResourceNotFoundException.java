package com.khalid.estore.exception;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public ResourceNotFoundException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
