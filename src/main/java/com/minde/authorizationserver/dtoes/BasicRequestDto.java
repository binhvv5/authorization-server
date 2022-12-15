package com.minde.authorizationserver.dtoes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



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
