package com.aoo.aar.application.service;

import com.aoo.common.application.service.ApplicationException;

public class AarException extends ApplicationException {

    public AarException(AarErrorCode error) {
        super(error);
    }

    public AarException(AarErrorCode error, String message) {
        super(error, message);
    }
}
