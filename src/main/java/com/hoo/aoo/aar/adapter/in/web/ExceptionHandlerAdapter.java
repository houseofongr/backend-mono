package com.hoo.aoo.aar.adapter.in.web;

import com.hoo.aoo.aar.application.exception.AarException;
import com.hoo.aoo.common.adapter.web.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdapter {

    @ExceptionHandler(AarException.class)
    public ResponseEntity<?> handler(AarException e) {
        log.error("AAR Exception has been occurred {}", e.toString());
        return ResponseEntity.status(e.getError().getStatus())
                .body(new MessageDto(e.getMessage()));
    }
}