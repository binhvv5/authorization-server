package com.minde.authorizationserver.dtoes.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponstDTO {
    private String accessToken;

    private long refreshIdx;

    private String userName;
}
