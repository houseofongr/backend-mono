package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.common.application.service.ApplicationException;
import lombok.Getter;

@Getter
public class AarException extends ApplicationException {


    public AarException(AarErrorCode error) {
        super(error);
    }

    public AarException(AarErrorCode error, String message) {
        super(error, message);
    }
}
