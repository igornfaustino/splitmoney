package com.nfaustino.splitmoney.shared.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    boolean success;
    T data;

    @Builder.Default
    String message = "";

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .success(true)
                .build();
    }

    public static ApiResponse<Void> internalServerError() {
        return ApiResponse.<Void>builder()
                .data(null)
                .success(false)
                .message("INTERNAL SERVER ERROR")
                .build();
    }

    public static ApiResponse<Void> validationError(String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .build();
    }

    public static ApiResponse<Void> badRequest(String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .build();
    }

    public static ApiResponse<Void> notFound(String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .build();
    }

}