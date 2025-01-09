package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.common.application.service.ApplicationException;

public class AdminException extends ApplicationException {

    public AdminException(AdminErrorCode error) {
        super(error);
    }

    public AdminException(AdminErrorCode error, String message) {
        super(error, message);
    }
}
