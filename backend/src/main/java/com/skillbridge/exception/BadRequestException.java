package com.skillbridge.exception;

/**
 * Thrown when the client sends invalid data, e.g. duplicate email on register,
 * or wrong password on login.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
