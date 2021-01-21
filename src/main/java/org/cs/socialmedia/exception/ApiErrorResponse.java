package org.cs.socialmedia.exception;

public class ApiErrorResponse {

    private int error;
    private String message;

    public ApiErrorResponse(int error, String message) {
        super();
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}