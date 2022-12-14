package com.minde.authorizationserver.services.auth.impl;

import com.minde.authorizationserver.dtoes.auth.UserDTO;
import com.minde.authorizationserver.mapper.TbdUserMapper;
import com.minde.authorizationserver.models.auth.TbdUser;
import com.minde.authorizationserver.repositories.auth.TbdUserRepository;
import com.minde.authorizationserver.services.auth.TbdUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TbdUserServiceImpl implements TbdUserService {
    private final TbdUserRepository tbdUserRepository;
    private final TbdUserMapper tbdUserMapper;

    @Override
    public Optional<UserDTO> findByUsername(String userName) {
        Optional<TbdUser> optional = tbdUserRepository.findByUsername(userName);
        return optional.map(tbdUserMapper::toDto);
    }
}
