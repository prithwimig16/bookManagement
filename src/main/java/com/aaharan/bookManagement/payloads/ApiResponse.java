package com.aaharan.bookManagement.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Long id;
    private String message;
    private Boolean success;

    public ApiResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ApiResponseDto {

        private Long id;

        private String phoneNumber;

        private String message;

        private Boolean success;

    }
}
