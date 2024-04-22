package com.fmt.notelock.infra.exceptions;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) { super(message);}
}
