package com.CustomerDataStore.Exceptions;

import java.util.NoSuchElementException;

public class MissingRequestDetailsException extends RuntimeException {
    public MissingRequestDetailsException(String message) {
        super(message);
    }
}
