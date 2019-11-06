package com.yanglei.exception;

public class ForeignAssociationException extends RuntimeException {
    public ForeignAssociationException() {
        super();
    }

    public ForeignAssociationException(String message) {
        super(message);
    }

    public ForeignAssociationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForeignAssociationException(Throwable cause) {
        super(cause);
    }

    public ForeignAssociationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
