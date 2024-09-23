package com.simeon.exceptions;

public class SerializationException extends RuntimeException {
    public SerializationException(Exception e){
        super(e);
    }
}
