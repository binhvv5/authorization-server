package com.minde.authorizationserver.dtoes.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private Boolean isActive;
    private List<AuthoritiesDTO> authorities;
}
