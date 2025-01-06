package com.hoo.aoo.aar.domain.exception;

public class NullIdException extends Exception {
    public NullIdException(String entityName) {
        super(entityName + " cannot own null ID.");
    }
}
