package com.minde.authorizationserver.common.securities;


import com.minde.authorizationserver.dtoes.auth.UserDTO;
import com.minde.authorizationserver.services.auth.TbdUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final TbdUserService tbdUserService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserDTO> optional = tbdUserService.findByUsername(username);
        return createSpringSecurityUser(username, optional);
    }

    private User createSpringSecurityUser(String username, Optional<UserDTO> optional) {
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }else{
            UserDTO userDTO = optional.get();
            List<GrantedAuthority> grantedAuthorities = userDTO
                    .getAuthorities()
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthName()))
                    .collect(Collectors.toList());
            return new User(username, userDTO.getPassword(), grantedAuthorities);
        }

    }
}
