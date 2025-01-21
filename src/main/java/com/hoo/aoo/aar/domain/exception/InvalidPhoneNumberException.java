package com.hoo.aoo.aar.domain.exception;

public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String phoneNumber) {
        super(phoneNumber + " is illegal phone number format.");
    }
}
