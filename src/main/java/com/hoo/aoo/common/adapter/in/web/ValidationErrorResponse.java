package com.hoo.aoo.common.adapter.in.web;

import org.springframework.validation.BindingResult;

public record ValidationErrorResponse(
        String fieldName,
        Object rejectedValue,
        String message
) {

    public static ValidationErrorResponse of(BindingResult bindingResult) {
        return new ValidationErrorResponse(
                bindingResult.getFieldError().getField(),
                bindingResult.getFieldError().getRejectedValue(),
                bindingResult.getFieldError().getDefaultMessage()
        );
    }
}
