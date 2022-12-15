package com.minde.authorizationserver.services.auth.impl;

import com.minde.authorizationserver.common.advice.AuthenticationException;
import com.minde.authorizationserver.common.advice.CustomException;
import com.minde.authorizationserver.common.consts.AuthorizationConst;
import com.minde.authorizationserver.common.consts.ExceptionConst;
import com.minde.authorizationserver.common.securities.filters.ReadableRequestWrapper;
import com.minde.authorizationserver.common.securities.provider.JwtProvider;
import com.minde.authorizationserver.dtoes.auth.AuthenDTO;
import com.minde.authorizationserver.services.auth.AuthenService;
import com.minde.authorizationserver.services.common.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenServiceImpl implements AuthenService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;
    private final HttpServletRequest request;

    @Override
    public AuthenDTO.LoginResponseDTO login(AuthenDTO.LoginRequestDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
            );

            Map<String, String> tokenParams = createTokenReturn(loginDto.getUserName());

            return AuthenDTO.LoginResponseDTO.builder()
                    .userName(tokenParams.get(AuthorizationConst.Authorizations.USER_NAME.getName()))
                    .refreshIdx(tokenParams.get(AuthorizationConst.Authorizations.REFRESH_INDEX.getName()))
                    .accessToken(tokenParams.get(AuthorizationConst.Authorizations.ACCESS_TOKEN.getName()))
                    .build();
        }catch (Exception e){
            throw new AuthenticationException(ExceptionConst.Constants.USERNAME_OR_PASSWORD_NOT_FOUND_EXCEPTION.getId());
        }
    }

    @Override
    public AuthenDTO.ExtendResponseDTO extend(AuthenDTO.ExtendRequestDTO extendDTO) {
        String refreshToken = commonService.getValueByKey(extendDTO.getRefreshIdx());
        if (jwtProvider.validateJwtToken(request, refreshToken)){
            Map<String, String> tokenParams = createTokenReturn(extendDTO.getUserName());
            commonService.deleteValueByKey(extendDTO.getRefreshIdx());
            return AuthenDTO.ExtendResponseDTO.builder()
                    .refreshIdx(tokenParams.get(AuthorizationConst.Authorizations.REFRESH_INDEX.getName()))
                    .accessToken(tokenParams.get(AuthorizationConst.Authorizations.ACCESS_TOKEN.getName()))
                    .build();
        }else{
            throw new CustomException("FAIL", "en");
        }
    }

    private Map<String, String> createTokenReturn(String username) {
        Map<String, String> result = new HashMap<>();

        result.put(AuthorizationConst.Authorizations.USER_NAME.getName(), username);

        String accessToken = jwtProvider.createAccessToken(username);
        Map<String, String> refreshTokenBean = jwtProvider.createRefreshToken(username);
        result.put(AuthorizationConst.Authorizations.ACCESS_TOKEN.getName(), accessToken);

        String idxRefresh = UUID.randomUUID().toString();
        result.put(AuthorizationConst.Authorizations.REFRESH_INDEX.getName(), idxRefresh);

        commonService.cachingKeyValue(idxRefresh, refreshTokenBean.get(AuthorizationConst.Authorizations.REFRESH_TOKEN.getName()));
        return result;
    }


}
