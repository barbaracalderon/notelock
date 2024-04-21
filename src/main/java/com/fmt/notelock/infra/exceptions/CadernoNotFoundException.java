package com.fmt.notelock.infra.exceptions;

public class CadernoNotFoundException extends RuntimeException{

    public CadernoNotFoundException(String message) {
        super(message);
    }

}
