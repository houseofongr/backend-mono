package com.aoo.admin.domain.exception;

public class BadHomeNameFormatException extends RuntimeException {
    public BadHomeNameFormatException(String homeName) {
        super(homeName + " is Bad Home name Format.");
    }
}
