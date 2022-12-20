package ru.kremenia.market.carts.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> exceptionHandler(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
