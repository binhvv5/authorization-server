package com.minde.authorizationserver.mapper;

import com.minde.authorizationserver.dtoes.auth.UserDTO;
import com.minde.authorizationserver.models.auth.TbdUser;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = TbdUserMapper.class)
@Component
public interface TbdUserMapper extends EntityMapper<UserDTO, TbdUser> {
}
