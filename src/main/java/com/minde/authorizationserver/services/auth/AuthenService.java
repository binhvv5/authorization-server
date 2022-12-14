package com.minde.authorizationserver.services.auth;


import com.minde.authorizationserver.dtoes.auth.LoginDTO;
import com.minde.authorizationserver.dtoes.auth.LoginResponstDTO;

public interface AuthenService {
    LoginResponstDTO login(LoginDTO userDto);
}
