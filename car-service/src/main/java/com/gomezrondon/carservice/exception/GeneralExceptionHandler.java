package com.gomezrondon.carservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Error> handleException(Exception ex) {
        MyCustomError myError = new MyCustomError(ex.getMessage());
        return new ResponseEntity(myError, HttpStatus.NOT_FOUND);
    }

}
