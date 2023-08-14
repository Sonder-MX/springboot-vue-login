package com.example.backendproject.entity;

public record RestBean<T>(int code, String message, T data) {

    public static <T> RestBean<T> success() {
        return new RestBean<>(200, "success", null);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, "success", data);
    }

    public static <T> RestBean<T> failure(int code, String msg) {
        return new RestBean<>(code, msg, null);
    }

    public String toJsonStr() {
        return String.format("{\"code\":%d,\"message\":\"%s\",\"data\":\"%s\"}", code, message,
                data == null ? "null" : data.toString());
    }

}
