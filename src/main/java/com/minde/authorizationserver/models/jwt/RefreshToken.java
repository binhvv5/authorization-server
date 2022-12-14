package com.minde.authorizationserver.models.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {

	private long idx;
    private String accessToken;
    private String refreshToken;
    private String refreshTokenExpirationAt;
    private String userId;
    private String lastLoginDt;
    private String lastLoginTm;
}
