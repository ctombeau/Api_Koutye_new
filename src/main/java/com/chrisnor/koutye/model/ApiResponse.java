package com.chrisnor.koutye.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
	private String status;
    private int code;
    private String message;
    private T data;

    public ApiResponse(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", 200, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>("error", code, message, null);
    }
}
