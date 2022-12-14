package com.minde.authorizationserver.common.consts;

import lombok.Getter;

public class AuthorizationConst {

    @Getter
    public enum Authorizations {
        REFRESH_TOKEN("refreshToken"),
        REFRESH_TOKEN_EXPIRATION("refreshTokenExpirationAt"),
        ACCESS_TOKEN("accessToken"),
        USER_NAME("userName"),
        REFRESH_INDEX("refreshIdx");

        private final String name;

        Authorizations(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
