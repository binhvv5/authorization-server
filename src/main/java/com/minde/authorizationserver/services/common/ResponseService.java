package com.minde.authorizationserver.services.common;


import com.minde.authorizationserver.common.utils.CommonUtil;
import com.minde.authorizationserver.dtoes.BasicResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final MessageSource  messageSource;

    /**
     *
     * @return
     */
    public BasicResponseDto getSuccessResult() {
        BasicResponseDto result = new BasicResponseDto();
        setSuccessResult(result);
        return result;
    }

    public BasicResponseDto getInsertSuccessResult(String locale) {
        BasicResponseDto result = new BasicResponseDto();
        result.setResponseCd(CommonUtil.getMessage(messageSource, "InsertSuccess.code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, "InsertSuccess.msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        return result;
    }

    public BasicResponseDto getDeleteSuccessResult(String locale) {
        BasicResponseDto result = new BasicResponseDto();
        result.setResponseCd(CommonUtil.getMessage(messageSource, "DeleteSuccess.code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, "DeleteSuccess.msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        return result;
    }

    public BasicResponseDto getUpdateSuccessResult(String locale) {
        BasicResponseDto result = new BasicResponseDto();
        result.setResponseCd(CommonUtil.getMessage(messageSource, "UpdateSuccess.code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, "UpdateSuccess.msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        return result;
    }

    /**
     *
     * @param code
     * @param locale
     * @return
     */
    public BasicResponseDto getFailResult(String code, String locale) {
        BasicResponseDto result = new BasicResponseDto();
        result.setResponseCd(CommonUtil.getMessage(messageSource, code + ".code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, code + ".msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        return result;
    }


    /**
     *
     */
    private void setSuccessResult(BasicResponseDto result) {
        result.setResponseCd("000000");
        result.setResponseMsg("OK.");
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
    }

    /**
     *
     */
    private void setFailResult(BasicResponseDto result, String code, String locale) {
        result.setResponseCd(CommonUtil.getMessage(messageSource, code + ".code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, code + ".msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
    }


}
