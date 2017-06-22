package com.kwetter.exception;

/**
 * Created by hein on 6/4/17.
 */
public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException(String message) {
        super(message);
    }
}
