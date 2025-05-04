package com.saurabh.fintrackbackend.exception;

public class DuplicateCreationException extends RuntimeException {
    public DuplicateCreationException(String message) {
        super(String.format("%s", message));
    }
}
