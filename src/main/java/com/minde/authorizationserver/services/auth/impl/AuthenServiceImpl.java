package com.minde.authorizationserver.services.auth.impl;

import com.minde.authorizationserver.common.advice.AuthenticationException;
import com.minde.authorizationserver.common.consts.AuthorizationConst;
import com.minde.authorizationserver.common.consts.ExceptionConst;
import com.minde.authorizationserver.common.securities.provider.JwtProvider;
import com.minde.authorizationserver.dtoes.auth.LoginDTO;
import com.minde.authorizationserver.dtoes.auth.LoginResponstDTO;
import com.minde.authorizationserver.services.auth.AuthenService;
import com.minde.authorizationserver.services.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenServiceImpl implements AuthenService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;

    @Override
    public LoginResponstDTO login(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
            );

            Map<String, String> tokenParams = createTokenReturn(loginDto);

            return LoginResponstDTO.builder()
                    .userName(tokenParams.get(AuthorizationConst.Authorizations.USER_NAME.getName()))
                    .refreshIdx(Long.parseLong(tokenParams.get(AuthorizationConst.Authorizations.REFRESH_INDEX.getName())))
                    .accessToken(tokenParams.get(AuthorizationConst.Authorizations.ACCESS_TOKEN.getName()))
                    .build();
        }catch (Exception e){
            throw new AuthenticationException(ExceptionConst.Constants.USERNAME_OR_PASSWORD_NOT_FOUND_EXCEPTION.getId());
        }
    }

    private Map<String, String> createTokenReturn(LoginDTO loginDTO) {
        Map<String, String> result = new HashMap<>();

        result.put(AuthorizationConst.Authorizations.USER_NAME.getName(), loginDTO.getUserName());

        String accessToken = jwtProvider.createAccessToken(loginDTO);
        Map<String, String> refreshTokenBean = jwtProvider.createRefreshToken(loginDTO);
//        String refreshToken = refreshTokenBean.get(AuthorizationConst.Authorizations.REFRESH_TOKEN.getName());
//        String refreshTokenExpirationAt = refreshTokenBean.get(AuthorizationConst.Authorizations.REFRESH_TOKEN_EXPIRATION.getName());

        result.put(AuthorizationConst.Authorizations.ACCESS_TOKEN.getName(), accessToken);
        result.put(AuthorizationConst.Authorizations.REFRESH_INDEX.getName(), "123");

        commonService.cachingKeyValue(loginDTO.getUserName(), refreshTokenBean.get(AuthorizationConst.Authorizations.REFRESH_TOKEN.getName()));
        return result;
    }


}
