package com.example.demo.exception;

public class NoUsersFoundException extends RuntimeException{
    public NoUsersFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
