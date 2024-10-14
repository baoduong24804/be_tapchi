package com.be.tapchi.pjtapchi.api;

public class ApiResponse<T> {
    private int status;      // Trạng thái HTTP
    private String message;  // Thông báo cho người dùng
    private T data;          // Dữ liệu trả về

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter và Setter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public void setData(T data) {
        this.data = data;
    }
}

