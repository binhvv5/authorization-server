package com.minde.authorizationserver.dtoes;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BasicRequiredDataRequestDto<T> extends BasicRequestDto {

    @Valid
    @NotNull
    private T data;
}
