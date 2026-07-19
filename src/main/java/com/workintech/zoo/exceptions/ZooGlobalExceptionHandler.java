package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(
            ZooException exception) {

        ZooErrorResponse response = new ZooErrorResponse(
                exception.getHttpStatus().value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(
                response,
                exception.getHttpStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleException(
            Exception exception) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ZooErrorResponse response = new ZooErrorResponse(
                status.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(response, status);
    }
}