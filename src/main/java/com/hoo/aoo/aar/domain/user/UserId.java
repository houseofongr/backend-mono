package com.hoo.aoo.aar.domain.user;

import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import lombok.Getter;

public class UserId {
    public static final String DEFAULT_PHONE_NUMBER = "010-0000-0000";

    @Getter
    private final String number;

    public UserId(String number) throws InvalidPhoneNumberException {
        if (number == null)
            this.number = DEFAULT_PHONE_NUMBER;
        else if (!number.matches("^010-\\d{4}-\\d{4}$")) {
            throw new InvalidPhoneNumberException(number);
        }
        else this.number = number;
    }

}
