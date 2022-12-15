package com.minde.authorizationserver.dtoes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BasicRequiredDataRequestDto<T> extends BasicRequestDto {

    @Valid
    @NotNull
    private T data;
}
