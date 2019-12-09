package com.syphan.practice.mns.api.exception;

public class SendSMSException extends RuntimeException {
    public SendSMSException(String message) {
        super(message);
    }

    public SendSMSException(Throwable cause) {
        super(cause);
    }

    public SendSMSException(String message, Throwable cause) {
        super(message, cause);
    }
}
