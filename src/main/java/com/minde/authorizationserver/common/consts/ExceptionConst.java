package com.minde.authorizationserver.common.consts;

import lombok.Getter;

public class ExceptionConst {
    public ExceptionConst() {
        throw new IllegalStateException("Utility class");
    }

    @Getter
    public enum Constants {
        FAIL("InternalServerError", "999999"),
        USERNAME_OR_PASSWORD_NOT_FOUND_EXCEPTION("UsernameOrPasswordNotFoundException", "S00001"),
        FORBIDDEN_EXCEPTION("ForbiddenException", "S00002"),
        UNAUTHORIZED_EXCEPTION("UNAUTHORIZEDException", "S00003"),
        EXPIRED_JWT_EXCEPTION("ExpiredJwtException", "S00004"),
        RE_LOGIN("UNAUTHORIZEDException", "S00005"),
        SIGNATURE_EXCEPTION("SignatureException", "S00006"),
        RECOVERY_EXCEPTION("RecoveryException", "S00007"),
        EMAIL_NOT_FOUND("EmailNotFound", "S00008"),
        OLD_PASSWORD_MATCHED("OldPasswordMatched", "S00009"),
        CURRENT_PASSWORD_NOT_MATCH("CurrentPasswordNotMatch", "S00010");

        private String id;
        private String code;

        Constants(String id, String code) {
            this.id = id;
            this.code = code;
        }
    }
}
