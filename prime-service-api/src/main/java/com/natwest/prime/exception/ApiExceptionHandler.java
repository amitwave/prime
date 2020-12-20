package com.natwest.prime.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler({InvalidRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> catchInvalidRequestException(InvalidRequestException e, WebRequest request) {
        return processException(e, request, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> invalidInput(ConstraintViolationException e, WebRequest request) {
        return processException(e, request, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> catchAll(Exception e, WebRequest request) {
        return processException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred.");
    }

    protected ResponseEntity<Object> processException(Exception e, WebRequest request, HttpStatus status, String errorMsg) {
        log.error("Exception ::", e);
        ErrorResponse msg = new ErrorResponse(status, errorMsg);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(request.getHeader("Accept")));
        return handleExceptionInternal(e, msg, headers, status, request);
    }

    public static class ErrorResponse {
        private final int status;
        private final String error;
        private final String message;

        public ErrorResponse(HttpStatus status, String errorMsg) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = errorMsg;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

    }
}
