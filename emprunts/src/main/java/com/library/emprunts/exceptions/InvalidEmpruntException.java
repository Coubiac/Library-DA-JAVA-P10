package com.library.emprunts.exceptions;

public class InvalidEmpruntException extends RuntimeException {
    private static final long serialVersionUID = 3156743245678932L;

    public InvalidEmpruntException(String message){
        super(message);
    }
}
