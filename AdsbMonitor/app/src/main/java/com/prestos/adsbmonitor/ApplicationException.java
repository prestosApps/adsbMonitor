package com.prestos.adsbmonitor;

/**
 * Created by prestos on 19/01/2016.
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
    }

    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }

    public ApplicationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }
}
