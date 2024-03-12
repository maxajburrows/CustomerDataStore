package com.CustomerDataStore.Controllers;

import com.CustomerDataStore.Exceptions.CustomerNotFoundException;
import com.CustomerDataStore.Exceptions.MissingRequestDetailsException;
import com.CustomerDataStore.Exceptions.NoCustomersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalErrorHandler {
    @ControllerAdvice
    public static class CustomerDataExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler({CustomerNotFoundException.class})
        protected ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }

        @ExceptionHandler({NoCustomersException.class})
        protected ResponseEntity handleNoCustomersException() {
            return ResponseEntity.noContent().build();
        }

        @ExceptionHandler({MissingRequestDetailsException.class})
        protected ResponseEntity<String> handleMissingDetailsException(MissingRequestDetailsException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
}
