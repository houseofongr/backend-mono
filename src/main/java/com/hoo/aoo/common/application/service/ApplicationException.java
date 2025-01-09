package com.hoo.aoo.common.application.service;

import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.admin.application.service.AdminErrorCode;

public abstract class ApplicationException extends RuntimeException {

  private final ErrorCode error;
  private final String message;

  public ApplicationException(ErrorCode error) {
    this.error = error;
    this.message = error.getMessage();
  }

  public ApplicationException(ErrorCode error, String message) {
    this.error = error;
    this.message = message;
  }

}
