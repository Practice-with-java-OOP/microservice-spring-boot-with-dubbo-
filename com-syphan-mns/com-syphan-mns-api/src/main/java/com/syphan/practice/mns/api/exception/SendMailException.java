package com.syphan.practice.mns.api.exception;

public class SendMailException extends RuntimeException {
    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendMailException(Throwable cause) {
        super(cause);
    }
}
