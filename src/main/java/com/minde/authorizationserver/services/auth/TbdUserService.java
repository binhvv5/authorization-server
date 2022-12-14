package com.minde.authorizationserver.services.auth;

import com.minde.authorizationserver.dtoes.auth.UserDTO;

import java.util.Optional;

public interface TbdUserService {
    Optional<UserDTO> findByUsername(String userName);
}
