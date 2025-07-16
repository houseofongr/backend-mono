package com.hoo.main.application.service;

import com.hoo.common.application.service.ApplicationException;

public class AarException extends ApplicationException {

    public AarException(AarErrorCode error) {
        super(error);
    }

    public AarException(AarErrorCode error, String message) {
        super(error, message);
    }
}
