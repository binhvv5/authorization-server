package com.minde.authorizationserver.dtoes.auth;

import lombok.Data;

@Data
public class LoginDTO {
    private String userName;
    private String password;
}
