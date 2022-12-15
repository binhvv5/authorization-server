package com.minde.authorizationserver.controllers;

import com.minde.authorizationserver.common.configs.properties.AuthorizationConfig;
import com.minde.authorizationserver.dtoes.auth.AuthenDTO;
import com.minde.authorizationserver.services.auth.AuthenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthorizationConfig authorizationConfig;
    private final AuthenService authenService;

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello: " + authorizationConfig.getName();
    }

    @PostMapping("/login")
    public AuthenDTO.LoginResponseDTO loginEndpoint(@RequestBody @Valid AuthenDTO.LoginRequestDTO loginDto) {
        return authenService.login(loginDto);
    }
    @PostMapping("/extend")
    public AuthenDTO.ExtendResponseDTO extendEndpoint(@RequestBody @Valid AuthenDTO.ExtendRequestDTO extendDTO){
        return authenService.extend(extendDTO);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Hello: " + authorizationConfig.getName();
    }
}
