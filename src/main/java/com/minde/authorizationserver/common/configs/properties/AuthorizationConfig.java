package com.minde.authorizationserver.common.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "authorization")
@Getter
@Setter
@Component
public class AuthorizationConfig {
    private String name;
}
