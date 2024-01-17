package com.ems.Employee_Management_System.customResponse;

public class CustomResponse<T> {

    private int status;
    private String message;

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

    public CustomResponse(int status, String message) {
        this.status = status;
        this.message = message;

    }

}