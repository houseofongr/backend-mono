package com.hoo.aoo.admin.domain.exception;

public class FloatBoundaryException extends Exception {

    public FloatBoundaryException(Float f) {
        super("Float : " + f + " has exceeded float boundary(.7f).");
    }
}
