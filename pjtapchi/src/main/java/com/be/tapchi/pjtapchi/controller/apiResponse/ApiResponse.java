package com.be.tapchi.pjtapchi.controller.apiResponse;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(Object data) {

       
            this.data = (T) data;
        
        
    }
}
