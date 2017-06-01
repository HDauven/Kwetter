package com.kwetter.exception;

/**
 * Created by hein on 4/3/17.
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
