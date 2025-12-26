package com.example.School_System.exceptions;

/**
 * Custom exception thrown when a user attempts to perform an action
 * they are not authorized to perform.
 *
 * This exception should be caught by the GlobalExceptionHandler
 * and returned as a 403 Forbidden HTTP response.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message the detail message explaining why authorization failed
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnauthorizedException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}