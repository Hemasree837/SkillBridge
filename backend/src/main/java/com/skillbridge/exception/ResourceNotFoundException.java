package com.skillbridge.exception;

/**
 * Thrown when a requested entity (User, Skill, SwapRequest) does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
