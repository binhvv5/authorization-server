package com.minde.authorizationserver.common.securities.filters;

import com.minde.authorizationserver.common.securities.provider.JwtProvider;
import com.minde.authorizationserver.common.utils.CommonUtil;
import com.minde.authorizationserver.common.utils.LogUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
public class CustomerFilter extends OncePerRequestFilter {
    private static final LogUtil loggerReq = new LogUtil(CustomerFilter.class, "[{}] [ REQ] - ");
    private static final LogUtil loggerRes = new LogUtil(CustomerFilter.class, "[{}] [ REQ] - ");
    private final JwtProvider jwtProvider;
    private static long start;
    private static long end;

    private static void logRequest(ReadableRequestWrapper request) throws IOException {
        loggerReq.addLogInfo("Remote IP 		: {}", CommonUtil.getIp( request));
        loggerReq.addLogInfo("Method 		: {}", (request.getMethod()));
        loggerReq.addLogInfo("URI 		: {}", (request.getRequestURI()));
        loggerReq.addLogInfo("Headers 		: {}", getHeaders(request));
        if (request.getContentLength() < 1000000) {
            loggerReq.addLogInfo("Payload 		: {}", logPayload(request.getContentType(), request.getInputStream()).replaceAll("\\R", "").replaceAll(" ", ""));
        } else {
            loggerReq.addLogInfo("Payload too big: {} bytes", request.getContentLength());
        }
    }

    private static void logResponse(ContentCachingResponseWrapper response) throws IOException {
        loggerRes.addLogInfo("Status 		: {}", response.getStatus());
        loggerRes.addLogInfo("Processing Time	: {}", (end - start) / 1000.0);
        loggerRes.addLogInfo("Payload 		: {}", logPayload(response.getContentType(), response.getContentInputStream()).replaceAll("\\R", "").replaceAll(" ", ""));
    }
    private static Map getHeaders(HttpServletRequest request) {
        Map headerMap = new HashMap<>();

        Enumeration headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = (String) headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }
    private static String logPayload(String contentType, InputStream inputStream) throws IOException {

        String contentString = "";
        boolean visible = isVisible(MediaType.valueOf(contentType == null ? "application/json" : contentType));
        if (visible) {
            byte[] content = StreamUtils.copyToByteArray(inputStream);
            if (content.length > 0) {
                contentString = new String(content);
            }
        } else {
            contentString = "Binary Content";
        }

        return contentString;
    }

    private static boolean isVisible(MediaType mediaType) {
        final List<MediaType> VISIBLE_TYPES = Arrays.asList(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml"),
                MediaType.MULTIPART_FORM_DATA
        );

        return VISIBLE_TYPES.stream()
                .anyMatch(visibleType -> visibleType.includes(mediaType));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        MDC.put("trace.id", UUID.randomUUID().toString());
        MDC.put("log.cnt", "0");
        if (isAsyncDispatch(request)) {

            filterChain.doFilter(request, response);
        } else {

            try {
                doFilterWrapped(new ReadableRequestWrapper(request),new ResponseWrapper(response),filterChain);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        logRequest(new ReadableRequestWrapper(request));
    }
    protected void doFilterWrapped(ReadableRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        try {

            start = System.currentTimeMillis();
            logRequest(request);

//            String token = jwtProvider.resolveToken((HttpServletRequest) request);
//
//            if (token != null && jwtProvider.validateJwtToken(request, token)) {
//
//                Authentication authentication = jwtProvider.getAuthentication(token);
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
            filterChain.doFilter(request, response);
        } finally {
            end = System.currentTimeMillis();
            logResponse(response);
            response.copyBodyToResponse();
        }
    }

}
