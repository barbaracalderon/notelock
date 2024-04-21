package com.fmt.notelock.infra.exceptions;

public class CadastroNotFoundException extends RuntimeException{

    public CadastroNotFoundException(String message) {
        super(message);
    }

}
