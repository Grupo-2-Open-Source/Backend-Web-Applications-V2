package com.autoya.autoya_api.autoya.mapper;

/**
 * Custom runtime exception class representing a resource not found.
 * Extends {@link RuntimeException} to indicate that the exception is unchecked.
 */
public class ResourceNotFoundException extends RuntimeException  {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
