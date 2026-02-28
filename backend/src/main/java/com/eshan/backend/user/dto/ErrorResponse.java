package com.eshan.backend.user.dto;

import java.util.Map;

public class ErrorResponse {
    private String error;
    private Map<String, String> details;

    // Constructor

    public ErrorResponse() {
    }

    public ErrorResponse(String error) {
        this.error = error;
    }


    //Getters and Setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
