package com.fmt.notelock.infra.exceptions;

public class PapelNotFoundException extends RuntimeException{

    public PapelNotFoundException(String message) {
        super(message);
    }

}
