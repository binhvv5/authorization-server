package com.minde.authorizationserver.dtoes.auth;

import lombok.Data;

@Data
public class AuthoritiesDTO {
    private Long authId;
    private Long userId;
    private String authName;
}
