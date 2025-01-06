package com.hoo.aoo.aar.domain.user;

import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import lombok.Getter;

public class PhoneNumber {

    @Getter
    private final String number;

    public PhoneNumber(String number) throws InvalidPhoneNumberException {
        if (!number.matches("^010-\\d{4}-\\d{4}$")) {
            throw new InvalidPhoneNumberException(number);
        }
        this.number = number;
    }

}
