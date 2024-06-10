package com.JJH.back.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionResolve {

    @ExceptionHandler({ResponseStatusException.class})
    public ExceptionResponse exceptionHandler(ResponseStatusException responseStatusException, HttpServletResponse httpServletResponse) {
        log.error(" ERROR 발생 내용 : " + responseStatusException.getReason());


        ExceptionResponse exceptionResponse = new ExceptionResponse();

        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorCode((HttpStatus) responseStatusException.getStatusCode());
        exceptionResponse.setErrorMessage(responseStatusException.getReason());

        httpServletResponse.setStatus(responseStatusException.getStatusCode().value());


        return exceptionResponse;
    }

}