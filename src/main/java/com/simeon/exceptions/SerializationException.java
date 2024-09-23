package com.simeon.exceptions;

public class SerializationException extends RuntimeException {
    public SerializationException(String message, Exception e){
        super(message, e);
    }

    public SerializationException(String message) {
        super(message);
    }
}
