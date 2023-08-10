package com.deliktas.internshipproject.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable clause) {
        super(message,clause);
    }

}
