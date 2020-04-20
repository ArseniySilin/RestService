package com.example.restservice;

public class Response {
    private final int code;
    private final String message;
    private final String data;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Response(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
