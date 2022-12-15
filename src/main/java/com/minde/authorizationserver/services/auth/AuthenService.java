package com.minde.authorizationserver.services.auth;


import com.minde.authorizationserver.dtoes.auth.AuthenDTO;

import java.io.IOException;

public interface AuthenService {
    AuthenDTO.LoginResponseDTO login(AuthenDTO.LoginRequestDTO userDto);

    AuthenDTO.ExtendResponseDTO extend(AuthenDTO.ExtendRequestDTO extendDTO);
}
