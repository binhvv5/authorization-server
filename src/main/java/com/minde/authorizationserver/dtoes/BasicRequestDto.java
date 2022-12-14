package com.minde.authorizationserver.dtoes;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BasicRequestDto {

    @NotBlank
    @Size(max = 255)
    private String clientId;

    @NotBlank
    @Size(max = 23)
    private String requestTs;

    @NotBlank
    @Size(max = 2)
    private String lang;
}
