package com.minde.authorizationserver.common.advice;


import com.minde.authorizationserver.common.utils.LogUtil;
import com.minde.authorizationserver.dtoes.BasicResponseDto;
import com.minde.authorizationserver.services.common.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdviceHandler {

    private final LogUtil logger = new LogUtil(this.getClass(), "[{}] [Exception] - ");

    private final ResponseService responseService;

    /**
     * @param exception
     * @return
     * @RequestBody, @RequestPart
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponseDto> handleException(Exception exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult("InternalServerError", "en");

        logger.addLogError("handleException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("handleException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("handleException Entity : {}", basicResponseDto.toString());
        logger.addLogError("handleException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponseDto> handleNullPointerException(NullPointerException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult("InternalServerError", "en");

        logger.addLogError("handleNullPointerException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("handleNullPointerException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("handleNullPointerException Entity : {}", basicResponseDto.toString());
        logger.addLogError("handleNullPointerException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponseDto> handleAuthenticationException(AuthenticationException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult(exception.getErrorCode(), exception.getLocale());

        logger.addLogError("HandleAuthenticationException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("HandleAuthenticationException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("HandleAuthenticationException Entity : {}", basicResponseDto.toString());
        logger.addLogError("HandleAuthenticationException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.OK);

    }

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponseDto> handleCustomException(CustomException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult(exception.getErrorCode(), exception.getLocale());

        logger.addLogError("HandleCustomException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("HandleCustomException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("HandleCustomException Entity : {}", basicResponseDto.toString());
        logger.addLogError("HandleCustomException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.OK);

    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<BasicResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult("MethodNotAllowed", "en");

        logger.addLogError("handleHttpRequestMethodNotSupportedException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("handleHttpRequestMethodNotSupportedException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("handleHttpRequestMethodNotSupportedException Entity : {}", basicResponseDto.toString());
        logger.addLogError("handleHttpRequestMethodNotSupportedException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult("EntityNotFound", "en");

        logger.addLogError("handleHttpMessageNotReadableException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("handleHttpMessageNotReadableException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("handleHttpMessageNotReadableException Entity : {}", basicResponseDto.toString());
        logger.addLogError("handleHttpMessageNotReadableException StackTrace : {}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.OK);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExceptionEntity> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        final ExceptionEntity infoPlusExceptionEntity = ExceptionEntity.of(ErrorCode.INVALID_INPUT_VALUE, exception.getBindingResult());
        logger.addLogError("HandleMethodArgumentNotValid Message : {}", exception.getMessage());
        logger.addLogDebug("HandleMethodArgumentNotValid Entity : {}", infoPlusExceptionEntity.toString());
        logger.addLogError("HandleMethodArgumentNotValid StackTrace : {}", exception.getMessage());
        return new ResponseEntity<>(infoPlusExceptionEntity, HttpStatus.OK);
    }

    @ExceptionHandler({RedisConnectionFailureException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BasicResponseDto> handleRedisConnectionFailureException(RedisConnectionFailureException exception) {

        BasicResponseDto basicResponseDto = responseService.getFailResult("RedisConnectionFail", "en");
        logger.addLogError("handleNullPointerException Code : {}", basicResponseDto.getResponseCd());
        logger.addLogError("handleNullPointerException Message : {}", basicResponseDto.getResponseMsg());
        logger.addLogDebug("handleNullPointerException Entity : {}", basicResponseDto.toString());
        logger.addLogError("handleNullPointerException StackTrace :{}", exception.getMessage());
        return new ResponseEntity<>(basicResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
