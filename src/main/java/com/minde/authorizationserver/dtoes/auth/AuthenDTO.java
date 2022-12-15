package com.minde.authorizationserver.dtoes.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


public class AuthenDTO {

    @Data
    @Builder
    public static class LoginResponseDTO {
        private String accessToken;

        private String refreshIdx;

        private String userName;
    }

    @Data
    public static class LoginRequestDTO {
        @NotNull
        @NotBlank
        private String userName;
        @NotNull
        @NotBlank
        private String password;
    }

    @Data
    public static class ExtendRequestDTO {
        @NotNull
        @NotBlank
        private String userName;
        @NotNull
        @NotBlank
        private String refreshIdx;
    }

    @Data
    @Builder
    public static class ExtendResponseDTO {
        private String accessToken;
        private String refreshIdx;
    }
}
