package com.minde.authorizationserver.common.securities.provider;


import com.minde.authorizationserver.common.consts.AuthorizationConst;
import com.minde.authorizationserver.common.securities.CustomUserDetailsService;
import com.minde.authorizationserver.dtoes.auth.LoginDTO;
import com.minde.authorizationserver.dtoes.auth.UserDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * JwtProvider
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private final long accessExpireTime = 15 * 60 * 1000L;   // 15m

    private long refreshExpireTime = 60 * 60 * 1000L; // 60m

    private final CustomUserDetailsService customUserDetailService;

    public String createAccessToken(LoginDTO loginDTO) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userName", loginDTO.getUserName());
//        payloads.put("userType", user.getUserType());

        Date expiration = new Date();


        expiration.setTime(expiration.getTime() + accessExpireTime);

        return Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Map<String, String> createRefreshToken(LoginDTO loginDTO) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userName", loginDTO.getUserName());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);

        String jwt = Jwts
                .builder()
                .setIssuer("AuthorizationServer")
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        Map<String, String> result = new HashMap<>();
        result.put("refreshToken", jwt);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserInfo(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(AuthorizationConst.Authorizations.USER_NAME.getName());
    }


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    public boolean validateJwtToken(ServletRequest request, String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (SignatureException e) {
            request.setAttribute("exception", "SignatureException");
        }
        return false;
    }
}
