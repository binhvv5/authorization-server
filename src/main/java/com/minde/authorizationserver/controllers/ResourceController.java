package com.minde.authorizationserver.controllers;

import com.minde.authorizationserver.common.configs.properties.AuthorizationConfig;
import com.minde.authorizationserver.dtoes.auth.LoginDTO;
import com.minde.authorizationserver.dtoes.auth.LoginResponstDTO;
import com.minde.authorizationserver.dtoes.auth.UserDTO;
import com.minde.authorizationserver.services.auth.AuthenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ResourceController {

    private final AuthorizationConfig authorizationConfig;
    private final AuthenService authenService;

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello: " + authorizationConfig.getName();
    }

    @PostMapping("/login")
    public LoginResponstDTO loginEndpoint(@RequestBody LoginDTO loginDto) {
        return authenService.login(loginDto);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Hello: " + authorizationConfig.getName();
    }
}
