package com.minde.authorizationserver.common.advice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ExceptionEntity {

    private String responseMsg;
    private String responseCd;
    private String responseTs;
    private List<FieldError> errors;

    private ExceptionEntity(final ErrorCode code, final List<FieldError> errors) {
        this.responseMsg = code.getMessage();
        this.errors = errors;
        this.responseCd = code.getCode();
        this.responseTs = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"));
    }

    private ExceptionEntity(final ErrorCode code) {
        this.responseMsg = code.getMessage();
        this.responseCd = code.getCode();
        this.errors = new ArrayList<>();
        this.responseTs = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"));
    }

    public static ExceptionEntity of(final ErrorCode code, final BindingResult bindingResult) {
        return new ExceptionEntity(code, FieldError.of(bindingResult));
    }

    public static ExceptionEntity of(final ErrorCode code) {
        return new ExceptionEntity(code);
    }

    public static ExceptionEntity of(final ErrorCode code, final List<FieldError> errors) {
        return new ExceptionEntity(code, errors);
    }

    public static ExceptionEntity of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ExceptionEntity.FieldError> errors = ExceptionEntity.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ExceptionEntity(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class FieldError {

        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }
        public static FieldError from(final String field, final String value, final String reason) {
            return new FieldError(field, value, reason);
        }
        
        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

}
