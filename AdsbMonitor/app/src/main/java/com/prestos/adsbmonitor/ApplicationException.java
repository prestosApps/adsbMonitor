package com.prestos.adsbmonitor;

import com.prestos.adsbmonitor.model.Errors;

/**
 * Created by prestos on 19/01/2016.
 */
public class ApplicationException extends Exception {

    private String userMessage;
    private Errors error;

    public ApplicationException() {
    }

    public ApplicationException(String detailMessage) {
        super(detailMessage);
    }

    public ApplicationException(String userMessage, Errors error, Throwable throwable) {
        super(throwable);
        this.userMessage = userMessage;
        this.error = error;
    }

    public ApplicationException(String detailMessage, String userMessage) {
        super(detailMessage);
        this.userMessage = userMessage;
    }

    public ApplicationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApplicationException(String detailMessage, String userMessage, Errors error, Throwable throwable) {
        super(detailMessage, throwable);
        this.userMessage = userMessage;
        this.error = error;
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }
}
