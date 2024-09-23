package com.simeon.exceptions;

public class HttpFormatException extends RuntimeException {
    public HttpFormatException(String cause, Exception e) {
        super(cause, e);
    }
}
