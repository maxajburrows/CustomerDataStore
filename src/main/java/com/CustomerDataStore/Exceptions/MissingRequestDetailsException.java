package com.CustomerDataStore.Exceptions;


public class MissingRequestDetailsException extends RuntimeException {
    public MissingRequestDetailsException(String message) {
        super(message);
    }
}
